package cn.bupt.runningplanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import java.util.ArrayList;
import java.util.List;

import cn.bupt.runningplanner.Util.AMapUtil;
import cn.bupt.runningplanner.classes.HomePage.routes.RouteData;
import cn.bupt.runningplanner.classes.HomePage.routes.RouteGenerate;
import cn.bupt.runningplanner.overlay.WalkRouteOverlay;

public class TestAlgorithmActivity extends AppCompatActivity implements RouteSearch.OnRouteSearchListener {

    AMap aMap = null;
    MapView mMapView = null;
    private RouteSearch routeSearch = null;
    private RouteSearch routeSearch1 = null;
    private ProgressDialog progDialog = null;// 搜索时进度条
    private WalkRouteResult mWalkRouteResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_algorithm);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }


//        //定位显示
//        MyLocationStyle myLocationStyle;
//        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
//        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        //步行路线规划
        routeSearch = new RouteSearch(this);
        routeSearch1 = new RouteSearch(this);
        //设置回调监听
        routeSearch.setRouteSearchListener(this);
        routeSearch1.setRouteSearchListener(this);
        LatLng latLngStart = new LatLng(39.960395,116.356351);

        //RouteData routeData = RouteGenerate.getCircleKeyPoints(latLngStart,5000,0,this);
//        List<LatLonPoint> keyPoints = new ArrayList<>();
//        for(int i=0;i<routeData.keyPoints.size();i++){
//            LatLonPoint point = new LatLonPoint(routeData.keyPoints.get(i).latitude,
//                    routeData.keyPoints.get(i).longitude);
//            keyPoints.add(point);
//        }
//
//        for(int i=0;i<keyPoints.size();i++){
//            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(keyPoints.get(i%keyPoints.size()),
//                    keyPoints.get((i+1)%keyPoints.size()));
//            //初始化query对象，fromAndTo是包含起终点信息，walkMode是步行路径规划的模式
//            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo,RouteSearch.WALK_DEFAULT);
//            routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
//        }
//

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }
    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }
    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        //隐藏进度条
        if (progDialog != null) {
            progDialog.dismiss();
        }
        //aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            this, aMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                } else if (result != null && result.getPaths() == null) {

                    Toast.makeText(TestAlgorithmActivity.this,"没有搜索到相关数据",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TestAlgorithmActivity.this,"没有搜索到相关数据",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(TestAlgorithmActivity.this,"出错了",Toast.LENGTH_SHORT).show();
        }
    }
}
