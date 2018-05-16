package cn.bupt.runningplanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.runningplanner.Util.Constants;
import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.classes.HomePage.history.HistoryAdapter;
import cn.bupt.runningplanner.classes.HomePage.history.HistoryItem;
import cn.bupt.runningplanner.entity.HistoryList;
import cn.bupt.runningplanner.entity.RouteInfo;
import cn.bupt.runningplanner.entity.UserInfo;
import okhttp3.Call;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.bupt.runningplanner.LoginandRegister.helper;

public class HistoryActivity extends AppCompatActivity {

    private String jsonContext;
    private Context self =this;
    private ObjectMapper mapper;
    private List<HistoryItem> mItemList = new ArrayList<>();
    HistoryList routeList;
    Message msg;
    RecyclerView recyclerView;
    String currentTime ="";
    long time=0;
    double distance=0;
    String route = "";
    HistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HistoryAdapter(mItemList,self);
        recyclerView.setAdapter(adapter);
    }

    public void init(){



        SharedPreferences preferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        String email = preferences.getString("email","");
        System.out.println(email);
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);

        try {
            mapper = new ObjectMapper();
            jsonContext =mapper.writeValueAsString(userInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpUtil.sendOkHttpRequest("http://"+ Constants.url+":8080/showHistory",jsonContext, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(HistoryActivity.this,"获取历史信息列表，请检查网络链接",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Looper.prepare();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            routeList = mapper.readValue(response.body().byteStream(), HistoryList.class);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        for(int i=0;i<routeList.getRouteInfoList().size();i++) {
                            currentTime = routeList.getRouteInfoList().get(i).getStartTime();
                            time = routeList.getRouteInfoList().get(i).getTime();
                            distance = routeList.getRouteInfoList().get(i).getTotalLong();
                            route = routeList.getRouteInfoList().get(i).getRoute();
                            HistoryItem historyItem = new HistoryItem(currentTime, distance, time, route);
                            mItemList.add(historyItem);
                        }
                        adapter.addList(mItemList);
                        adapter.notifyDataSetChanged();
                    }
                });
                Looper.loop();
            }
        });


    }
    private void ThreadFinish(){
        adapter.addList(mItemList);
        adapter.notifyDataSetChanged();
    }
//    //线程使用的handler  创建一个线程来进行实时显示总公里数
//    @SuppressLint("HandlerLeak")
//    private android.os.Handler handler = new android.os.Handler(){
//        @SuppressLint("SetTextI18n")
//        public void handleMessage(android.os.Message msg){
//            switch (msg.what){
//                case 1:
//
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
}
