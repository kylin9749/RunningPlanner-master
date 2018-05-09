package cn.bupt.runningplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.google.gson.Gson;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertableAppCompatActivity;
import cn.bupt.runningplanner.classes.HomePage.run.RunningData;
import cn.bupt.runningplanner.classes.HomePage.user.UserInfo;
import java.text.DecimalFormat;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cn.bupt.runningplanner.LoginandRegister.helper;

public class RunningFinishActivity extends AlertableAppCompatActivity {
    private RunningFinishActivity self = this;
    private RunningData runningData;
    private AMap map;
    private UserInfo info = new UserInfo();

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

        String mail ="123456789@qq.com";
        SharedPreferences pref = getSharedPreferences("data", Context.MODE_PRIVATE);
        mail= pref.getString("email","123456789@qq.com");
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("insert into RoutInformation values(?,?,?,?,?,?)", new Object[]{mail, runningData.startTime, runningData.endTime, (runningData.endTime - runningData.startTime), (info.weight / runningData.length * 1.036), runningData.length});

        Cursor cursor = db.rawQuery("select * from PersonInformation where Email=?", new String[]{mail});
        boolean result =cursor.moveToNext();
        if(result){
            int number = cursor.getInt(10) + 1;


            long currentTime = cursor.getLong(7);

            long totalTime = currentTime + runningData.endTime - runningData.startTime;

            double currentLong = cursor.getDouble(8);
            double totalLong = currentLong + runningData.length;

            long currentCalorie = cursor.getLong(9);
            long totalCalorie = (long)((info.weight / runningData.length * 1.036) + currentCalorie) ;

            db.execSQL("UPDATE PersonInformation SET totalTime=?,totalLong=?,totalCalorie=?,Number=? where Email=?", new Object[]{totalTime, totalLong, totalCalorie, number, mail});
            cursor.close();
            db.close();
        }



        ((Button) findViewById(R.id.back_button)).setOnClickListener(onClickListener);
    }
    private String timeFormat(long mills) {
        int h, m, s;
        h = (int) (mills / 1000 / 3600 % 24);
        m = (int) (mills / 1000 / 60 % 60);
        s = (int) (mills / 1000 % 60);
        return "" + h + "时" + m + "分" + s + "秒";
    }
}