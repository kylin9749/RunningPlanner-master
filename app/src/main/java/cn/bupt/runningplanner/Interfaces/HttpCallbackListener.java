package cn.bupt.runningplanner.Interfaces;
//此接口用于http访问线程的回调
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
