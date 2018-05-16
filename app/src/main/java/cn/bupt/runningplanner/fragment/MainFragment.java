package cn.bupt.runningplanner.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Random;

import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.RunningPreparationActivity;
import cn.bupt.runningplanner.Util.Constants;
import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.entity.Result;
import cn.bupt.runningplanner.entity.UserInfo;
import okhttp3.Call;
import okhttp3.Response;

import static cn.bupt.runningplanner.LoginandRegister.helper;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    UserInfo userInfoResult;
    TextView total_mails = null;
    ObjectMapper mapper = null;
    String jsonContext = null;
    Message msg = null;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_music, container, false);

        CardView cardView = (CardView)view.findViewById(R.id.card1);
        cardView.setCardBackgroundColor(Color.argb(0,0,0,0));
        total_mails = (TextView)view.findViewById(R.id.totalmails);




        //从sharedpreferences里获取数据
        SharedPreferences preferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String email = preferences.getString("email","");

        final UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);

        try {
            mapper = new ObjectMapper();
            jsonContext =mapper.writeValueAsString(userInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpUtil.sendOkHttpRequest("http://"+ Constants.url+"/show",jsonContext,new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(),"网络开小差咯，暂时无法获取路程信息，请检查网络连接",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            @Override
            public void onResponse(Call call,Response response) throws IOException {
                userInfoResult = mapper.readValue(response.body().byteStream(), UserInfo.class);
                    if(userInfoResult.getResultCode()==1){
                        msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
            }
        });

        Button button = (Button)view.findViewById(R.id.run);
        button.setOnClickListener(this);

        return view;
    }

    //线程使用的handler  创建一个线程来进行实时显示总公里数
    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler(){
        @SuppressLint("SetTextI18n")
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case 1:
                    total_mails.setText((((double)userInfoResult.getTotalLong())/1000)+"");

                    //将当前数据写入sharedpreference
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putString("name", userInfoResult.getName());
                    editor.putString("sex",userInfoResult.getSex());
                    editor.putInt("age",userInfoResult.getAge());
                    editor.putInt("totalLong",userInfoResult.getTotalLong());
                    editor.putInt("high",userInfoResult.getHigh());
                    editor.putInt("weight",userInfoResult.getWeight());
                    editor.putInt("totalTime",userInfoResult.getTotalTime());
                    editor.putInt("totalCalorie",userInfoResult.getTotalCalorie());
                    editor.putInt("number",userInfoResult.getNumber());

                    editor.apply();//提交修改

            }
        }
    };

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), RunningPreparationActivity.class));
        getActivity().finish();
    }
}
