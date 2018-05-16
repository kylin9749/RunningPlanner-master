package cn.bupt.runningplanner;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import cn.bupt.runningplanner.Util.Constants;
import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.entity.Result;

public class ModifySex extends AppCompatActivity {
    private ModifySex self = this;
    private ImageView male;
    private ImageView female;
    private String email;
    private String sex;
    ObjectMapper mapper = null;
    String jsonContext = null;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.modify_cancel:
                    intent = new Intent();
                    intent.setClass(self, BodyActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.modify_finish:
                    intent = new Intent();
                    changeSex();
                    intent.setClass(self, BodyActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.modify_male:
                    male.setVisibility(ImageView.VISIBLE);
                    female.setVisibility(ImageView.GONE);
                    sex="0";
                    break;
                case R.id.modify_female:
                    female.setVisibility(ImageView.VISIBLE);
                    male.setVisibility(ImageView.GONE);
                    sex="1";
                    break;
                default:
                    break;
            }
        }
    };

    private void changeSex() {
        //通过http将数据写入数据库
        cn.bupt.runningplanner.entity.UserInfo userInfoWrite = new cn.bupt.runningplanner.entity.UserInfo();

        userInfoWrite.setEmail(email);
        userInfoWrite.setSex(sex);
        userInfoWrite.setUpdateSource(6);//设定数据源为sex

        try {
            mapper = new ObjectMapper();
            jsonContext =mapper.writeValueAsString(userInfoWrite);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpUtil.sendOkHttpRequest("http://"+ Constants.url+":8080/update",jsonContext, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ModifySex.this,"数据上传失败，请检查网络链接",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                Result result = mapper.readValue(response.body().byteStream(), Result.class);

                if (result.getResultCode() == Result.SUCCESS) {
                    Looper.prepare();
                    Toast.makeText(ModifySex.this,"数据上传成功",Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_modify_sex);
        male = ((ImageView)findViewById(R.id.modify_male_check));
        female = ((ImageView)findViewById(R.id.modify_female_check));

        email = getIntent().getStringExtra("email");

        ((ImageView)findViewById(R.id.modify_male)).setOnClickListener(onClickListener);
        ((ImageView)findViewById(R.id.modify_female)).setOnClickListener(onClickListener);
        ((TextView)findViewById(R.id.modify_cancel)).setOnClickListener(onClickListener);
        ((TextView)findViewById(R.id.modify_finish)).setOnClickListener(onClickListener);
    }
}
