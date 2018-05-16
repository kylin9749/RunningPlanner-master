package cn.bupt.runningplanner.classes.HomePage.history;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryItem {

    private String currentTime;
    private double distance;
    private long time;
    private String route;


    public  HistoryItem(String currentTime,double distance,long time,String route){
        this.currentTime=currentTime;
        this.distance = distance;
        this.time=time;
        this.route = route;
    }
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }





    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
