package cn.bupt.runningplanner.classes.HomePage.alert;

public class AlertMessage {
    private String title;
    private String detail;

    public AlertMessage(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}