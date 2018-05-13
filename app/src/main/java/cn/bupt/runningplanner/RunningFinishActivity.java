package cn.bupt.runningplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertableAppCompatActivity;
import cn.bupt.runningplanner.classes.HomePage.run.RunningData;
import cn.bupt.runningplanner.classes.HomePage.user.UserInfo;

import java.io.IOException;
import java.text.DecimalFormat;


import cn.bupt.runningplanner.entity.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cn.bupt.runningplanner.LoginandRegister.helper;

public class RunningFinishActivity extends AlertableAppCompatActivity {
    private RunningFinishActivity self = this;
    private RunningData runningData;
    private AMap map;
    private UserInfo info = new UserInfo();
    ObjectMapper mapper = null;
    String jsonContext = null;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.back_button:
                    Intent intent = new Intent();
                    intent.setClass(self, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_finish);

        this.runningData = new Gson().fromJson(getIntent().getStringExtra("running_data"), RunningData.class);

        info.weight = getIntent().getIntExtra("user_weight", 0);

        ((TextView) findViewById(R.id.length_text_view)).setText("" + runningData.length + "米");
        ((TextView) findViewById(R.id.time_text_view)).setText(timeFormat(runningData.endTime - runningData.startTime));
        ((TextView) findViewById(R.id.kalorie_txt_view)).setText((new DecimalFormat("######0.00")).format(info.weight / runningData.length * 1.036) + "千卡");

        //从sharedpreferences里获取数据
        SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int number = preferences.getInt("number",0) +1;

        int currentTime = preferences.getInt("totalTime",0);
//            long currentTime = cursor.getLong(7);
        int totalTime = currentTime + (int)(runningData.endTime - runningData.startTime);

        int currentLong = preferences.getInt("totalLong",0);
//            double currentLong = cursor.getDouble(8);
        int totalLong = currentLong + (int)runningData.length;

        int currentCalorie = preferences.getInt("totalCalorie",0);
//           long currentCalorie = cursor.getLong(9);
        int totalCalorie = (int)((info.weight / runningData.length * 1036) + currentCalorie) ;
//            db.execSQL("UPDATE PersonInformation SET totalTime=?,totalLong=?,totalCalorie=?,Number=? where Email=?", new Object[]{totalTime, totalLong, totalCalorie, number, mail});


        //通过http将数据写入数据库
        cn.bupt.runningplanner.entity.UserInfo userInfoWrite = new cn.bupt.runningplanner.entity.UserInfo();

        userInfoWrite.setTotalLong(totalLong);
        userInfoWrite.setTotalTime(totalTime);
        userInfoWrite.setTotalCalorie(totalCalorie);
        userInfoWrite.setNumber(number);
        userInfoWrite.setEmail(preferences.getString("email",""));
        userInfoWrite.setUpdateSource(1);//设定数据源为runningFinishActivity

        try {
            mapper = new ObjectMapper();
            jsonContext =mapper.writeValueAsString(userInfoWrite);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpUtil.sendOkHttpRequest("http://10.128.202.97:8080/update",jsonContext, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(RunningFinishActivity.this,"数据上传失败，请检查网络链接",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                Result result = mapper.readValue(response.body().byteStream(), Result.class);

                if (result.getResultCode() == Result.SUCCESS) {
                    Looper.prepare();
                    Toast.makeText(RunningFinishActivity.this,"数据上传成功",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else {
                    Looper.prepare();
                    Looper.loop();

                }
            }
        });


        (findViewById(R.id.back_button)).setOnClickListener(onClickListener);
    }
    private String timeFormat(long mills) {
        int h, m, s;
        h = (int) (mills / 1000 / 3600 % 24);
        m = (int) (mills / 1000 / 60 % 60);
        s = (int) (mills / 1000 % 60);
        return "" + h + "时" + m + "分" + s + "秒";
    }
}