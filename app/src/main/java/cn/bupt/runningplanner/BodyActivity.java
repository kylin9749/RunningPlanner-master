package cn.bupt.runningplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.bupt.runningplanner.classes.HomePage.alert.AlertMessage;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertableAppCompatActivity;
import cn.bupt.runningplanner.classes.HomePage.user.UserInfo;

import static cn.bupt.runningplanner.LoginandRegister.helper;

public class BodyActivity extends AlertableAppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private BodyActivity self = this;
    private UserInfo info;



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_savedata:
                    int age, weight, height;
                    try {
                        age = Integer.parseInt(((EditText) findViewById(R.id.input_age)).getText().toString());
                    } catch (Exception ex) {
                        alert(new AlertMessage("请正确输入年龄", ""));
                        break;
                    }

                    if (age <= 0 || age >= 100) {
                        alert(new AlertMessage("请正确输入年龄", ""));
                        break;
                    }

                    try {
                        weight = Integer.parseInt(((EditText) findViewById(R.id.input_weight)).getText().toString());
                    } catch (Exception ex) {
                        alert(new AlertMessage("请正确输入体重", ""));
                        break;
                    }

                    if (weight <= 0 || weight >= 500) {
                        alert(new AlertMessage("请正确输入体重", ""));
                        break;
                    }

                    try {
                        height = Integer.parseInt(((EditText) findViewById(R.id.input_height)).getText().toString());
                    } catch (Exception ex) {
                        alert(new AlertMessage("请正确输入身高", ""));
                        break;
                    }

                    if (height <= 100 || height >= 250) {
                        alert(new AlertMessage("请正确输入身高", ""));
                        break;
                    }


                    info.name=((EditText)findViewById(R.id.input_name)).getText().toString();
                    info.age = age;
                    info.weight = weight;
                    info.height = height;

                    String mail="123456789@qq.com";
                    SharedPreferences pref = getSharedPreferences("data", Context.MODE_PRIVATE);
                    mail= pref.getString("email","123456789@qq.com");
                    SQLiteDatabase db = helper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("select * from PersonInformation where Email=?", new String[]{mail});
                    boolean result =cursor.moveToNext();

                    if(result){
                        db.execSQL("UPDATE PersonInformation SET userName=?,Age=?,Weight=?,High=?,Sex=? where Email=?", new Object[]{info.name, info.age, info.weight, info.height,info.sex,mail});
                        cursor.close();
                        db.close();
                    }

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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.male:
                info.sex='0';
                break;
            case R.id.female:
                info.sex='1';
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        info = new UserInfo();
        info.sex='0';
        setTitle("填写信息");
        ((Button) findViewById(R.id.female)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.male)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.btn_savedata)).setOnClickListener(onClickListener);

        RadioGroup sexRadioGroup = (RadioGroup)findViewById(R.id.rgSex);
        sexRadioGroup.setOnCheckedChangeListener(this) ;
        RadioButton male = (RadioButton)findViewById(R.id.male);
        RadioButton female = (RadioButton)findViewById(R.id.female);

        EditText username = (EditText)findViewById(R.id.input_name);
        EditText age = (EditText)findViewById(R.id.input_age);
        EditText weight = (EditText)findViewById(R.id.input_weight);
        EditText height = (EditText)findViewById(R.id.input_height);

        String mail="123456789@qq.com";
        SharedPreferences pref = getSharedPreferences("data", Context.MODE_PRIVATE);
        mail= pref.getString("email","123456789@qq.com");
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from PersonInformation where Email=?", new String[]{mail});
        boolean result =cursor.moveToNext();

        if(result){
            username.setText(cursor.getString(1));
            age.setText(cursor.getString(4));
            weight.setText(cursor.getString(6));
            height.setText(cursor.getString(5));
            cursor.close();
            db.close();
        }

    }
}
