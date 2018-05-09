package cn.bupt.runningplanner.classes.HomePage.routes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatlngPosition {
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
