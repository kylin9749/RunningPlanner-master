package cn.bupt.runningplanner;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import cn.bupt.runningplanner.classes.HomePage.alert.AlertMessage;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertableAppCompatActivity;

public class RunningPreparationActivity extends AlertableAppCompatActivity implements AMap.OnMyLocationChangeListener{
    private RunningPreparationActivity self = this;
    private double longitude;
    private double latitude;
    private MyLocationStyle myLocationStyle = null;
    private AMap aMap = null;
    private MapView mapView = null;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.route_choose_Button:
                    try {
                        int a = Integer.parseInt(((EditText) findViewById(R.id.length_edit_text)).getText().toString());
                    } catch (Exception e) {
                        alert(new AlertMessage("请输入正确的跑步路程长度", ""));
                        break;
                    }
                    ((Button) findViewById(R.id.route_choose_Button)).setEnabled(false);
                    ((Button) findViewById(R.id.route_choose_Button)).setText("路线生成中");
                    Intent intent = new Intent();
                    intent.putExtra("length", ((EditText) findViewById(R.id.length_edit_text)).getText().toString());
                    intent.putExtra("lat", latitude);
                    intent.putExtra("lng", longitude);
                    intent.setClass(self, RouteSelectActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_preparation);

        ((Button) findViewById(R.id.route_choose_Button)).setOnClickListener(onClickListener);

        mapView = ((MapView) findViewById(R.id.locate_map));

        mapView.onCreate(savedInstanceState);

        ((EditText) findViewById(R.id.length_edit_text)).setInputType(EditorInfo.TYPE_CLASS_PHONE);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }

        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {

        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.radiusFillColor(0);
        myLocationStyle.strokeWidth(0);
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if(location != null) {
            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if(bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);

                /*
                errorCode
                errorInfo
                locationType
                */
                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType );
            } else {
                Log.e("amap", "定位信息， bundle is null ");

            }
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
        } else {
            Log.e("amap", "定位失败");
        }
    }
}
