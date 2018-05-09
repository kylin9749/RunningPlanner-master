package cn.bupt.runningplanner.Util;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;

import cn.bupt.runningplanner.Interfaces.HttpCallbackListener;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

//此工具类用于向服务器发送http请求
public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Object obj;
    //使用HttpURLConnection的方法请求http响应,，，，,尚未完成，由于okhttp更好用，此方法只写了一半
    public static String sendHttpRquestwithHttpURLConnection(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection =null;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    return null;
    }


    //使用okhttp的方法请求http响应
    public static void sendOkHttpRequest(String address,String jsonContext,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonContext);
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
