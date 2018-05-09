package cn.bupt.runningplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import cn.bupt.runningplanner.classes.HomePage.history.HistoryAdapter;
import cn.bupt.runningplanner.classes.HomePage.history.HistoryItem;
import cn.bupt.runningplanner.classes.HomePage.waterfulAdapter;
import cn.bupt.runningplanner.classes.HomePage.waterfulItem;

import java.util.ArrayList;
import java.util.List;

import static cn.bupt.runningplanner.LoginandRegister.helper;

public class HistoryActivity extends AppCompatActivity {

    private List<HistoryItem> mItemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView2);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        HistoryAdapter adapter = new HistoryAdapter(mItemList,this);
        recyclerView.setAdapter(adapter);
    }

    public void init(){
        String mail ="123456789@qq.com";
        SharedPreferences pref = getSharedPreferences("data", Context.MODE_PRIVATE);
        mail= pref.getString("email","123456789@qq.com");
        SQLiteDatabase db = helper.getWritableDatabase();
        long currentTime =0;
        long time=0;
        double distance=0;

        Cursor cursor = db.rawQuery("select * from RoutInformation where Email=?", new String[]{mail});

        while(cursor.moveToNext()){
            currentTime = cursor.getLong(1);
            time=cursor.getLong(3);
            distance = cursor.getDouble(5);
            System.out.println(currentTime + time + distance);
            HistoryItem historyItem = new HistoryItem(currentTime,distance,time);
            mItemList.add(historyItem);
        }

    }
}
