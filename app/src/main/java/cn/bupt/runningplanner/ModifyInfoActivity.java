package cn.bupt.runningplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import cn.bupt.runningplanner.Util.Constants;
import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertMessage;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertableAppCompatActivity;
import cn.bupt.runningplanner.entity.Result;

public class ModifyInfoActivity extends AlertableAppCompatActivity {
    private ModifyInfoActivity self = this;
    ObjectMapper mapper = null;
    String jsonContext = null;
    private String title;
    private String origin;
    private String email;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.modify_delete:
                    ((TextView)findViewById(R.id.modify_origin)).setText("");
                    break;
                case R.id.modify_cancel:
                    intent = new Intent();
                    intent.setClass(self, BodyActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.modify_finish:
                    if(((EditText) findViewById(R.id.modify_origin)).getText().toString().equals("")){
                        Toast.makeText(ModifyInfoActivity.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        intent = new Intent();
                        changeInfo();
                        intent.setClass(self, BodyActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void changeInfo() {
        //通过http将数据写入数据库
        SharedPreferences preferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        cn.bupt.runningplanner.entity.UserInfo userInfoWrite = new cn.bupt.runningplanner.entity.UserInfo();
        userInfoWrite.setEmail(email);
            switch (title) {
                case "昵称":
                    String name = ((EditText) findViewById(R.id.modify_origin)).getText().toString();
                    userInfoWrite.setName(name);
                    userInfoWrite.setUpdateSource(2);//设定数据源为修改name
                    editor.putString("name",name);
                    break;
                case "年龄":
                    try {
                        int age = Integer.parseInt(((EditText) findViewById(R.id.modify_origin)).getText().toString());
                        if (age <= 0 || age >= 100)
                            Toast.makeText(ModifyInfoActivity.this,"请正确输入年龄",Toast.LENGTH_SHORT).show();
                        userInfoWrite.setAge(age);
                        userInfoWrite.setUpdateSource(3);//设定数据源为修改age
                        editor.putInt("age",age);
                    } catch (Exception ex) {
                        Toast.makeText(ModifyInfoActivity.this,"请正确输入年龄",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "身高(CM)":
                    try {
                        int height = Integer.parseInt(((EditText) findViewById(R.id.modify_origin)).getText().toString());
                        if (height <= 100 || height >= 250) {
                            Toast.makeText(ModifyInfoActivity.this,"请正确输入身高",Toast.LENGTH_SHORT).show();
                        }
                        userInfoWrite.setHigh(height);
                        userInfoWrite.setUpdateSource(4);//设定数据源为修改height
                        editor.putInt("high",height);
                    } catch (Exception ex) {
                        Toast.makeText(ModifyInfoActivity.this,"请正确输入身高 ",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "体重(KG)":
                    try {
                        int weight = Integer.parseInt(((EditText) findViewById(R.id.modify_origin)).getText().toString());
                        if (weight <= 0 || weight >= 500) {
                            Toast.makeText(ModifyInfoActivity.this,"请正确输入体重",Toast.LENGTH_SHORT).show();
                        }
                        userInfoWrite.setWeight(weight);
                        userInfoWrite.setUpdateSource(5);//设定数据源为修改name
                        editor.putInt("weight",weight);
                    } catch (Exception ex) {
                        Toast.makeText(ModifyInfoActivity.this,"请正确输入体重",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
        }
        editor.apply();
        try {
            mapper = new ObjectMapper();
            jsonContext =mapper.writeValueAsString(userInfoWrite);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpUtil.sendOkHttpRequest("http://"+ Constants.url+"/update",jsonContext, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ModifyInfoActivity.this,"数据上传失败，请检查网络链接",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                Result result = mapper.readValue(response.body().byteStream(), Result.class);

                if (result.getResultCode() == Result.SUCCESS) {
                    Looper.prepare();
                    Toast.makeText(ModifyInfoActivity.this,"数据上传成功",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else {
                    Looper.prepare();
                    Looper.loop();

                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        title = getIntent().getStringExtra("title");
        origin = getIntent().getStringExtra("origin");
        email = getIntent().getStringExtra("email");

        ((TextView)findViewById(R.id.modify_title)).setText(title);
        ((TextView)findViewById(R.id.modify_origin)).setText(origin);
        (findViewById(R.id.modify_cancel)).setOnClickListener(onClickListener);
        (findViewById(R.id.modify_finish)).setOnClickListener(onClickListener);
        (findViewById(R.id.modify_delete)).setOnClickListener(onClickListener);
    }
}