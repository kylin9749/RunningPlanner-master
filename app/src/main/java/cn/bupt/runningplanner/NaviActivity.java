package cn.bupt.runningplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.CheckPermissionsActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviLatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.bupt.runningplanner.classes.HomePage.routes.RouteData;

public class NaviActivity extends BaseActivity{
    private NaviActivity self = this;

    private double Slatitude;
    private double Slongitude;
    private double Elatitude;
    private double Elongitude;
    private RouteData routeData;
    private int times = RunningNaviActivity.times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        routeData = new Gson().fromJson(getIntent().getStringExtra("route_data"), RouteData.class);
        int size = routeData.ployPoints.size();
        Slatitude = routeData.ployPoints.get(size*(times-1)/8).latitude;
        Slongitude = routeData.ployPoints.get(size*(times-1)/8).longitude;
        Elatitude = routeData.ployPoints.get(size*(times)/8-1).latitude;
        Elongitude = routeData.ployPoints.get(size*(times)/8-1).longitude;

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        mAMapNavi.calculateWalkRoute(new NaviLatLng(Slatitude, Slongitude), new NaviLatLng(Elatitude, Elongitude));
    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }


    @Override
    public void onEndEmulatorNavi() {
        super.onEndEmulatorNavi();
        finish();
    }

    @Override
    public void onArriveDestination() {
        super.onArriveDestination();
        finish();
    }
}