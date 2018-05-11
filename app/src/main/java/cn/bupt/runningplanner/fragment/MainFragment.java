package cn.bupt.runningplanner.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import cn.bupt.runningplanner.MainActivity;
import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.RunningPreparationActivity;
import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.entity.ReadRequest;
import okhttp3.Call;
import okhttp3.Response;

import static cn.bupt.runningplanner.LoginandRegister.helper;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {


    public MainFragment() {
        // Required empty public constructor
    }

    ObjectMapper mapper = null;
    String jsonContext = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        CardView cardView = (CardView)view.findViewById(R.id.card1);
        cardView.setCardBackgroundColor(Color.argb(0,0,0,0));


        SQLiteDatabase db = helper.getWritableDatabase();
        TextView total_mails = (TextView)view.findViewById(R.id.totalmails);
        //Cursor cursor = db.rawQuery("select * from PersonInformation where Email=?", new String[]{mail});
        //boolean result =cursor.moveToNext();

        ReadRequest readRequest = new ReadRequest();

        try {
            mapper = new ObjectMapper();
            jsonContext =mapper.writeValueAsString(readRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpUtil.sendOkHttpRequest("http://10.128.202.97:8080/auth",jsonContext,new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(),"网络开小差咯，暂时无法获取路程信息，请检查网络连接",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


            }
        });
        //total_mails.setText(new Double(currentLong).toString());



        Button button = (Button)view.findViewById(R.id.run);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), RunningPreparationActivity.class));
    }
}
