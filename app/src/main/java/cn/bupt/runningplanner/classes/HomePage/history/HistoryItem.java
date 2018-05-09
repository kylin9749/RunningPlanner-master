package cn.bupt.runningplanner.classes.HomePage.history;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryItem {

    private long currentTime;
    private double distance;
    private long time;


    public  HistoryItem(long currentTime,double distance,long time){
        this.currentTime=currentTime;
        this.distance = distance;
        this.time=time;
    }
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
