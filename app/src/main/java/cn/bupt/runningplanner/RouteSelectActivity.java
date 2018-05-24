package cn.bupt.runningplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;

import cn.bupt.runningplanner.Util.Transform;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertMessage;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertableAppCompatActivity;
import cn.bupt.runningplanner.classes.HomePage.routes.Merchant;
import cn.bupt.runningplanner.classes.HomePage.routes.MerchantInfoDialog;
import cn.bupt.runningplanner.classes.HomePage.routes.RouteData;
import cn.bupt.runningplanner.classes.HomePage.routes.RouteGenerate;
import cn.bupt.runningplanner.classes.HomePage.routes.RouteSchematicDiagramLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RouteSelectActivity extends AlertableAppCompatActivity implements AMap.OnMapScreenShotListener {

    public RouteSelectActivity self = this;
    private Bundle savedInstanceState;
    private MapView mainMapView;
    private LinearLayout routeSchemaDiagramContainer;
    private ArrayList<RouteSchematicDiagramLayout> routeSchematicDiagramList = new ArrayList<RouteSchematicDiagramLayout>();
    private RouteSchematicDiagramLayout selectedDiagram = null;

    //data for search route
    private int length;
    private double longitude;
    private double latitude;

    //marker-merchant map
    private HashMap<Marker, Merchant> markerMerchantHashMap = new HashMap<Marker, Merchant>();

    //高德
    AMap amap;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_navi_button:
                    if (selectedDiagram == null) {
                        alert(new AlertMessage("错误", "请先选择路线再点击开始"));
                    } else {
                        /**
                         * 对地图进行截屏
                         */
                        amap.getMapScreenShot(self);

                        Intent intent = new Intent();
                        intent.putExtra("route_data", new Gson().toJson(selectedDiagram.getRouteData()));
                        intent.setClass(self, RunningNaviActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                default:
                    for (RouteSchematicDiagramLayout diagram : routeSchematicDiagramList) {
                        if (v == diagram || v == diagram || v == diagram.getTextView()) {
                            for (RouteSchematicDiagramLayout d : routeSchematicDiagramList)
                                d.chooseView(false);
                            diagram.chooseView(true);
                            RouteData routeDataToDraw = diagram.getRouteData();
                            mainMapView.getMap().clear();
                            //start/stop marker
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(routeDataToDraw.keyPoints.get(0));
                            markerOptions.draggable(false);
                            markerOptions.title("开始/终止点");
                            mainMapView.getMap().addMarker(markerOptions);

                            //merchant marker
                            for (int i = 0; i < routeDataToDraw.isMerchant.size(); i++) {
                                if (routeDataToDraw.isMerchant.get(i)) {
                                    markerOptions = new MarkerOptions();
                                    markerOptions.position(routeDataToDraw.keyPoints.get(i));
                                    markerOptions.draggable(false);
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.shop_logo));
                                    Marker marker = mainMapView.getMap().addMarker(markerOptions);
                                    markerMerchantHashMap.put(marker, routeDataToDraw.merchantInfo.get(i));
                                }
                            }

                            PolylineOptions polylineOptions = new PolylineOptions();
                            polylineOptions.setPoints(routeDataToDraw.ployPoints);
                            mainMapView.getMap().addPolyline(polylineOptions);
                            mainMapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(routeDataToDraw.centerPoint));
                            mainMapView.getMap().moveCamera(CameraUpdateFactory.zoomTo(15));
                            amap = mainMapView.getMap();
                            selectedDiagram = diagram;
                            break;
                        }
                    }
                    break;
            }
        }
    };

    private AMap.OnMarkerClickListener onMarkerClickListener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Merchant merchant = markerMerchantHashMap.get(marker);
            if (merchant != null) {
                new MerchantInfoDialog(self, merchant).show();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_select);
        this.savedInstanceState = savedInstanceState;

        //get data for search route
        length = Integer.parseInt(getIntent().getStringExtra("length"));
        longitude = getIntent().getDoubleExtra("lng", 0);
        latitude = getIntent().getDoubleExtra("lat", 0);

        this.setTitle("选择您喜欢的路线");

        //init ui
        findViewById(R.id.start_navi_button).setOnClickListener(onClickListener);
        this.mainMapView = (MapView) findViewById(R.id.main_map);
        this.routeSchemaDiagramContainer = (LinearLayout) findViewById(R.id.route_schema_diagram_container);
        this.mainMapView.onCreate(savedInstanceState);
        amap =mainMapView.getMap();
        amap.setOnMarkerClickListener(onMarkerClickListener);
        searchRoute();

        //将
    }

    public void addSchematicDiagram(RouteSchematicDiagramLayout routeSchematicDiagramLayout) {
        this.routeSchematicDiagramList.add(routeSchematicDiagramLayout);
        this.routeSchemaDiagramContainer.addView(routeSchematicDiagramLayout);
        routeSchematicDiagramLayout.setOnClickListener(onClickListener);
    }

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }


//    PoiSearch.Query query;
    //使用poi搜索搜索该点周围的标志性建筑物，然后替换该点
//    public void poiSearch(LatLng point){
//        //使用poi搜索将点拉取到附近的标志性建筑物
//        //当前点进行poi搜索
//        query = null;
//        PoiSearch poiSearch = null;
//        query = new PoiSearch.Query("",
//                "180400|190300|190500|190600|200000|140000|060000|050000|070000|080000|100000|110000|120000",
//                "");
//        //"180400|190300|190500|190600|200000|140000|060000|050000|070000|080000|100000|110000|120000"
//        //keyWord表示搜索字符串，
//        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
//        query.setPageSize(1);// 设置每页最多返回多少条poiitem
//        query.setPageNum(0);//设置查询页码
//
//        //构造 PoiSearch 对象，并设置监听。
//
//
//        LatLonPoint lp = new LatLonPoint(point.latitude,point.longitude);
//        //发送poi搜索请求
//        poiSearch.setBound(new PoiSearch.SearchBound(lp, 500, true));//
//        poiSearch.searchPOIAsyn();
//    }

    private void searchRoute() {
            RouteGenerate.generateRoute(
                    new LatLng(latitude, longitude),
                    length,
                    RouteGenerate.MIDDLE,
                    self
            );

        RouteGenerate.generateRoute(
                new LatLng(latitude, longitude),
                length,
                RouteGenerate.NORTH,
                self
        );
        RouteGenerate.generateRoute(
                new LatLng(latitude, longitude),
                length,
                RouteGenerate.SOUTH,
                self
        );
        RouteGenerate.generateRoute(
                new LatLng(latitude, longitude),
                length,
                RouteGenerate.WEST,
                self
        );
        RouteGenerate.generateRoute(
                new LatLng(latitude, longitude),
                length,
                RouteGenerate.EAST,
                self
        );

    }

    @Override
    public void onMapScreenShot(Bitmap bitmap) {

    }

    @Override
    public void onMapScreenShot(Bitmap bitmap, int arg1) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if(null == bitmap){
            return;
        }
        try {
            String route =Environment.getExternalStorageDirectory() + "/test_" + sdf.format(new Date()) + ".png";
            String fileName = "/test_" + sdf.format(new Date()) + ".png";
            FileOutputStream fos = new FileOutputStream(route);

            boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            //将当前数据写入sharedpreference
            SharedPreferences sharedPreferences = getSharedPreferences("routeInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.putString("route",fileName);

            editor.apply();//提交修改

            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuffer buffer = new StringBuffer();
            if (b) {
//                buffer.append("截屏成功 ");
            }
            else {
//                buffer.append("截屏失败 ");
            }
            if (arg1 != 0) {
//                buffer.append("地图渲染完成，截屏无网格");
            }
            else {
//                buffer.append( "地图未渲染完成，截屏有网格");
            }

            Toast.makeText(RouteSelectActivity.this,buffer.toString(),Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}