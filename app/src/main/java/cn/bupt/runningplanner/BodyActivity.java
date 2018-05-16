package cn.bupt.runningplanner;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BodyActivity extends AppCompatActivity {
    private BodyActivity self = this;
    private Intent intent;

    SharedPreferences preferences;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = null;
            switch (v.getId()) {
                case R.id.change_age:
                    title = "年龄";

                    intent.putExtra("title", title);
                    intent.putExtra("origin", age);
                    intent.setClass(self, ModifyInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.change_height:
                    title = "身高(CM)";
                    intent.putExtra("title", title);
                    intent.putExtra("origin", height);
                    intent.setClass(self, ModifyInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.change_name:
                    title = "昵称";
                    intent.putExtra("title", title);
                    intent.putExtra("origin", name);
                    intent.setClass(self, ModifyInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.change_sex:
                    title = "性别";
                    intent.putExtra("title", title);
                    intent.putExtra("origin", sex);
                    intent.setClass(self, ModifySex.class);
                    startActivity(intent);
                    break;
                case R.id.change_weight:
                    title = "体重(KG)";
                    intent.putExtra("title", title);
                    intent.putExtra("origin", weight);
                    intent.setClass(self, ModifyInfoActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.change_head:
                    break;
                default:
                    break;
            }
        }
    };

    TextView show_age;
    TextView show_height;
    TextView show_name;
    TextView show_weight;
    TextView show_sex;
    ImageView show_head;

    String age;
    String name;
    String height;
    String weight;
    String sex;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);


        (findViewById(R.id.change_age)).setOnClickListener(onClickListener);
        (findViewById(R.id.change_height)).setOnClickListener(onClickListener);
        (findViewById(R.id.change_name)).setOnClickListener(onClickListener);
        (findViewById(R.id.change_sex)).setOnClickListener(onClickListener);
        (findViewById(R.id.change_weight)).setOnClickListener(onClickListener);
        (findViewById(R.id.change_head)).setOnClickListener(onClickListener);

        show_age = (TextView) findViewById(R.id.input_age);
        show_height = (TextView) findViewById(R.id.input_height);
        show_name = (TextView) findViewById(R.id.input_name);
        show_weight = (TextView) findViewById(R.id.input_weight);
        show_sex = (TextView) findViewById(R.id.input_sex);
        //获取sharedpreference
        preferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        //读取数据
        email = preferences.getString("email","");

        age = preferences.getInt("age",0)+"";
        if(preferences.getString("sex","").equals("0")){
            sex = "男";
        }else {
            sex = "女";
        }
        weight = preferences.getInt("weight",0)+"";
        height = preferences.getInt("high",0)+"";
        name = preferences.getString("name","");

        //将email传递到下一个界面
        intent = new Intent();
        intent.putExtra("email", email);
        //展示在界面
        show_age.setText(age);
        show_sex.setText(sex);
        show_weight.setText(weight);
        show_height.setText(height);
        show_name.setText(name);
    }
}