package cn.bupt.runningplanner.classes.HomePage.routes;



import android.annotation.SuppressLint;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;

import com.amap.api.maps.model.LatLng;

import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import cn.bupt.runningplanner.RouteSelectActivity;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertMessage;

import java.util.ArrayList;



/**
 * Created by yisic on 2017/7/9.
 */

public class RouteGenerate {
    public static final int NORTH = 0;
    public static final int NORTHEAST = 1;
    public static final int EAST = 2;
    public static final int SOUTHEAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTHWEST = 5;
    public static final int WEST = 6;
    public static final int NORTHWEST = 7;
    public static final int MIDDLE = 8;






    public static RouteData getCircleKeyPoints(LatLng startPoint, double distance, int orient,final RouteSelectActivity activity) {
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        //平衡路线规划增加的路径长度
        if (distance < 5000) {
            distance *= 0.6;
        } else if (distance < 7000) {
            distance *= 0.7;
        } else {
            distance *= 0.8;
        }

        //n,w,s,e
        LatLng n, s, w, e, nw, ne, sw, se, c;
        //
        double tempLat, tempLng, tempDis;
        switch (orient) {
            case MIDDLE:
                //以我为中心点
                c = new LatLng(startPoint.latitude,startPoint.longitude);
                //求出北方的点
                tempLat = c.latitude;
                tempLng = c.longitude;
                while(true){
                    tempDis = LatLngCalculate.getDistance(c.latitude,c.longitude,tempLat,tempLng);
                    if(tempDis > distance/(2*Math.PI)){
                        break;
                    }
                    else {
                        tempLat +=0.000001;
                    }
                }
                    n= new LatLng(tempLat,tempLng);
                //南面的点
                tempLat = c.latitude;
                tempLng = c.longitude;
                while(true){
                    tempDis = LatLngCalculate.getDistance(c.latitude,c.longitude,tempLat,tempLng);
                    if(tempDis > distance/(2*Math.PI)){
                        break;
                    }
                    else {
                        tempLat -=0.000001;
                    }
                }

                s= new LatLng(tempLat,tempLng);


                //西面的点
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLng -= 0.000001;
                }
                w = new LatLng(tempLat,tempLng);


                //东面的点
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLng += 0.000001;
                }
                e = new LatLng(tempLat,tempLng);
                break;
            case NORTH:
                s = new LatLng(startPoint.latitude, startPoint.longitude);
                tempLat = s.latitude;
                tempLng = s.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(s.latitude, s.longitude, tempLat, tempLng);
                    if (tempDis > distance / Math.PI)
                        break;
                    else
                        tempLat += 0.000001;
                }
                n = new LatLng(tempLat, tempLng);
                c = new LatLng((s.latitude + n.latitude) / 2, (s.longitude + n.longitude) / 2);
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLng -= 0.000001;
                }
                w = new LatLng(tempLat, tempLng);
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLng += 0.000001;
                }
                e = new LatLng(tempLat, tempLng);
                break;
            case SOUTH:
                n = new LatLng(startPoint.latitude, startPoint.longitude);
                tempLat = n.latitude;
                tempLng = n.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(n.latitude, n.longitude, tempLat, tempLng);
                    if (tempDis > distance / Math.PI)
                        break;
                    else
                        tempLat -= 0.000001;
                }
                s = new LatLng(tempLat, tempLng);
                c = new LatLng((s.latitude + n.latitude) / 2, (s.longitude + n.longitude) / 2);
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLng -= 0.000001;
                }
                w = new LatLng(tempLat, tempLng);
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLng += 0.000001;
                }
                e = new LatLng(tempLat, tempLng);
                break;
            case WEST:
                e = new LatLng(startPoint.latitude, startPoint.longitude);
                tempLat = e.latitude;
                tempLng = e.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(e.latitude, e.longitude, tempLat, tempLng);
                    if (tempDis > distance / Math.PI)
                        break;
                    else
                        tempLng -= 0.000001;
                }
                w = new LatLng(tempLat, tempLng);
                c = new LatLng((e.latitude + w.latitude) / 2, (e.longitude + w.longitude) / 2);
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLat += 0.000001;
                }
                n = new LatLng(tempLat, tempLng);
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLat -= 0.000001;
                }
                s = new LatLng(tempLat, tempLng);
                break;
            case EAST:
                w = new LatLng(startPoint.latitude, startPoint.longitude);
                tempLat = w.latitude;
                tempLng = w.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(w.latitude, w.longitude, tempLat, tempLng);
                    if (tempDis > distance / Math.PI)
                        break;
                    else
                        tempLng += 0.000001;
                }
                e = new LatLng(tempLat, tempLng);
                c = new LatLng((e.latitude + w.latitude) / 2, (e.longitude + w.longitude) / 2);
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLat += 0.000001;
                }
                n = new LatLng(tempLat, tempLng);
                tempLat = c.latitude;
                tempLng = c.longitude;
                while (true) {
                    tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
                    if (tempDis > distance / (Math.PI * 2))
                        break;
                    else
                        tempLat -= 0.000001;
                }
                s = new LatLng(tempLat, tempLng);
                break;
            default:
                return null;
        }

        //nw,ne,sw,se
        tempLat = c.latitude;
        tempLng = c.longitude;
        while (true) {
            tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
            if (tempDis > distance / (Math.PI * 2))
                break;
            else {
                tempLat += 0.000001;
                tempLng -= 0.000001;
            }
        }
        nw = new LatLng(tempLat, tempLng);
        tempLat = c.latitude;
        tempLng = c.longitude;
        while (true) {
            tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
            if (tempDis > distance / (Math.PI * 2))
                break;
            else {
                tempLat += 0.000001;
                tempLng += 0.000001;
            }
        }
        ne = new LatLng(tempLat, tempLng);
        tempLat = c.latitude;
        tempLng = c.longitude;
        while (true) {
            tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
            if (tempDis > distance / (Math.PI * 2))
                break;
            else {
                tempLat -= 0.000001;
                tempLng -= 0.000001;
            }
        }
        sw = new LatLng(tempLat, tempLng);
        tempLat = c.latitude;
        tempLng = c.longitude;
        while (true) {
            tempDis = LatLngCalculate.getDistance(c.latitude, c.longitude, tempLat, tempLng);
            if (tempDis > distance / (Math.PI * 2))
                break;
            else {
                tempLat -= 0.000001;
                tempLng += 0.000001;
            }
        }

        se = new LatLng(tempLat, tempLng);



        switch (orient) {
            case MIDDLE:
                points.add(c);
//                points.add(s);
                points.add(sw);
                points.add(w);
                points.add(nw);
                points.add(n);
                points.add(ne);
                points.add(e);
                points.add(se);
//                points.add(s);
                points.add(c);
                break;
            case NORTH:
                points.add(s);
                points.add(sw);
                points.add(w);
                points.add(nw);
                points.add(n);
                points.add(ne);
                points.add(e);
                points.add(se);
                points.add(s);
                break;
            case SOUTH:
                points.add(n);
                points.add(ne);
                points.add(e);
                points.add(se);
                points.add(s);
                points.add(sw);
                points.add(w);
                points.add(nw);
                points.add(n);
                break;
            case WEST:
                points.add(e);
                points.add(se);
                points.add(s);
                points.add(sw);
                points.add(w);
                points.add(nw);
                points.add(n);
                points.add(ne);
                points.add(e);
                break;
            case EAST:
                points.add(w);
                points.add(nw);
                points.add(n);
                points.add(ne);
                points.add(e);
                points.add(se);
                points.add(s);
                points.add(sw);
                points.add(w);
                break;
            default:
                return null;
        }
        //RouteData类存储本条路线的信息
        RouteData routeData = new RouteData();
        routeData.centerPoint = c;
        routeData.keyPoints = points;
        return routeData;
    }


    public static void generateRoute(final LatLng startPoint, double distance, int orient, final RouteSelectActivity activity) {
       final RouteData routeData = getCircleKeyPoints(startPoint, distance, orient,activity);



        //amap search
        final RouteSearch routeSearch = new RouteSearch(activity);
        final ArrayList<RouteSearch.FromAndTo> fromAndToList = new ArrayList<RouteSearch.FromAndTo>();
        ArrayList<RouteSearch.WalkRouteQuery> queryList = new ArrayList<>();

        for (int i = 0; i < routeData.keyPoints.size() - 1; i++) {
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                    new LatLonPoint(routeData.keyPoints.get(i).latitude, routeData.keyPoints.get(i).longitude),
                    new LatLonPoint(routeData.keyPoints.get(i + 1).latitude, routeData.keyPoints.get(i + 1).longitude)
            );
            fromAndToList.add(fromAndTo);
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(
                    new RouteSearch.FromAndTo(
                            new LatLonPoint(routeData.keyPoints.get(i).latitude, routeData.keyPoints.get(i).longitude),
                            new LatLonPoint(routeData.keyPoints.get(i + 1).latitude, routeData.keyPoints.get(i + 1).longitude)
                    )
            );
            queryList.add(query);
        }

        //query listener
        routeSearch.setRouteSearchListener(new RunnerRouteSearchListener(queryList.size()) {
            @Override
            public void successHandler(WalkRouteResult walkRouteResult) {
                int index;
                for (index = 0; index < fromAndToList.size(); index++) {
                    RouteSearch.FromAndTo fromAndTo1 = walkRouteResult.getWalkQuery().getFromAndTo();
                    RouteSearch.FromAndTo fromAndTo2 = fromAndToList.get(index);
                    if (fromAndTo1.getFrom().getLatitude() == fromAndTo2.getFrom().getLatitude()
                            && fromAndTo1.getFrom().getLongitude() == fromAndTo2.getFrom().getLongitude()) {
                        break;
                    }
                }
                resultList[index] = walkRouteResult;
                responseCounter++;
                if (responseCounter >= resultList.length) {
                    polyProcess();
                }
            }

            @Override
            public void errorHandler() {
                activity.alert(new AlertMessage("路线规划出错", ""));
            }

            @SuppressLint("SetTextI18n")
            private void polyProcess() {
                //从所有结果中提取多边形顶点
                ArrayList<LatLng> polyResourcePoints = new ArrayList<LatLng>();
                for (WalkRouteResult result : this.resultList) {
                    if (result == null)
                        continue;
                    WalkPath path = result.getPaths().get(result.getPaths().size() - 1);
                    for (WalkStep step : path.getSteps()) {
                        for (LatLonPoint rawPoint : step.getPolyline()) {
                            polyResourcePoints.add(new LatLng(rawPoint.getLatitude(), rawPoint.getLongitude()));
                        }
                    }
                }
                //删除重复路径
                Boolean[] deleteFlag = new Boolean[polyResourcePoints.size()];
                for (int i = 0; i < deleteFlag.length; i++)
                    deleteFlag[i] = false;
                //路线剪枝
                ArrayList<LatLng> optimizedPoints = new ArrayList<LatLng>();
                for (int i = 0, j; i < polyResourcePoints.size() - 1; i++) {
                    for (j = i + 1; j < polyResourcePoints.size(); j++) {
                        if (LatLngCalculate.isSamePoint(polyResourcePoints.get(i), polyResourcePoints.get(j))
                                && (j - i) <= polyResourcePoints.size() / 3) {
                            //剪枝
                            for (int deleteIndex = i + 1; deleteIndex <= j; deleteIndex++) {
                                deleteFlag[deleteIndex] = true;
                            }
                        }
                    }
                }
                for (int i = 0; i < polyResourcePoints.size(); i++) {
                    if (!deleteFlag[i])
                        optimizedPoints.add(polyResourcePoints.get(i));
                }
                polyResourcePoints = optimizedPoints;
                if (!LatLngCalculate.isSamePoint(polyResourcePoints.get(0), polyResourcePoints.get(polyResourcePoints.size() - 1)))
                    //强行修正最后一段路线
                    polyResourcePoints.add(new LatLng(polyResourcePoints.get(0).latitude, polyResourcePoints.get(0).longitude));


                //add polyline to schematic diagram map view
                routeData.length = LatLngCalculate.getPathLength(polyResourcePoints);
                routeData.ployPoints = polyResourcePoints;
                RouteSchematicDiagramLayout diagram = new RouteSchematicDiagramLayout(activity, activity.getSavedInstanceState());
                diagram.setRouteData(routeData);
                AMap map = diagram.getMapView().getMap();
                diagram.getTextView().setText("" + routeData.length + "M");
                diagram.getMapView().onCreate(activity.getSavedInstanceState());
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.setPoints(polyResourcePoints);
                map.addPolyline(polylineOptions);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(startPoint);
                markerOptions.draggable(false);
                map.addMarker(markerOptions).setClickable(false);
                map.moveCamera(CameraUpdateFactory.changeLatLng(routeData.centerPoint));
                map.moveCamera(CameraUpdateFactory.zoomTo(12));
                map.getUiSettings().setZoomControlsEnabled(false);
                map.getUiSettings().setAllGesturesEnabled(false);
                activity.addSchematicDiagram(diagram);
            }
        });

        //asyn query
        for (RouteSearch.WalkRouteQuery query : queryList)
            routeSearch.calculateWalkRouteAsyn(query);

    }



}
