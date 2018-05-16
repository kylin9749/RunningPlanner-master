package cn.bupt.runningplanner.entity;

import java.util.ArrayList;
import java.util.List;

public class HistoryList {
    private List<RouteInfo> routeInfoList;

    public  HistoryList(){
        routeInfoList = new ArrayList<>();
    }
    public List<RouteInfo> getRouteInfoList() {
        return routeInfoList;
    }

    public void setRouteInfoList(List<RouteInfo> routeInfoList) {
        this.routeInfoList = routeInfoList;
    }
}
