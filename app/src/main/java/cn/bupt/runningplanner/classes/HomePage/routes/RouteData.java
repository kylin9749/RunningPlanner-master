package cn.bupt.runningplanner.classes.HomePage.routes;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;


public class RouteData implements Serializable {
    public ArrayList<LatLng> keyPoints = new ArrayList<LatLng>();
    public ArrayList<Boolean> isMerchant = new ArrayList<Boolean>();
    public ArrayList<Merchant> merchantInfo = new ArrayList<Merchant>();
    public ArrayList<LatLng> ployPoints = new ArrayList<LatLng>();
    public LatLng centerPoint;
    public double length;
//    public WalkRouteResult result;
}
