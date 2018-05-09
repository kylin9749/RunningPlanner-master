package cn.bupt.runningplanner.entity;

/**
 * Created by 天道 北云 on 2018/5/1.
 */
public class Result {
    public static final int SUCCESS = 1;
    public static final int FAIL = -1;

    private int resultCode;

    public Result() {}

    public Result(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
