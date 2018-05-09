package cn.bupt.runningplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import cn.bupt.runningplanner.classes.HomePage.alert.AlertMessage;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertableAppCompatActivity;

public class RunningPreparationActivity extends AlertableAppCompatActivity {
    private RunningPreparationActivity self = this;
    private double longitude;
    private double latitude;
    private String address;
    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override

        public void onLocationChanged(AMapLocation aMapLocation) {
            ( findViewById(R.id.route_choose_Button)).setEnabled(true);
            ((TextView) findViewById(R.id.locate_text_view)).setText(aMapLocation.getAddress());
            address = aMapLocation.getAddress();
            latitude = aMapLocation.getLatitude();
            longitude = aMapLocation.getLongitude();
            AMap map = ((MapView) findViewById(R.id.locate_map)).getMap();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(latitude, longitude));
            markerOptions.draggable(false);
            markerOptions.title("您的位置");
            Marker marker = map.addMarker(markerOptions);
            marker.showInfoWindow();
            marker.setClickable(false);
            map.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
            map.moveCamera(CameraUpdateFactory.zoomTo(16));
            map.getUiSettings().setZoomControlsEnabled(false);
            map.getUiSettings().setAllGesturesEnabled(false);
        }
    };
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
                    intent.putExtra("address", address);
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
        ((Button) findViewById(R.id.route_choose_Button)).setEnabled(false);

        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setOnceLocation(true);
//        aMapLocationClientOption.setOnceLocationLatest(true);

        AMapLocationClient aMapLocationClient = new AMapLocationClient(this);
        aMapLocationClient.setLocationListener(aMapLocationListener);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();

        ((MapView) findViewById(R.id.locate_map)).onCreate(savedInstanceState);

        ((EditText) findViewById(R.id.length_edit_text)).setInputType(EditorInfo.TYPE_CLASS_PHONE);
    }
}
