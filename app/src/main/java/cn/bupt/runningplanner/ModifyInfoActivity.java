package cn.bupt.runningplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bupt.runningplanner.classes.HomePage.alert.AlertMessage;
import cn.bupt.runningplanner.classes.HomePage.alert.AlertableAppCompatActivity;

public class ModifyInfoActivity extends AlertableAppCompatActivity {
    private ModifyInfoActivity self = this;

    private String title;
    private String origin;

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
                    intent = new Intent();
                    changeInfo();
                    intent.setClass(self, BodyActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void changeInfo() {

        if (title == "昵称") {
            String name = ((EditText)findViewById(R.id.modify_origin)).getText().toString();
        }

        else if (title == "年龄") {
            try {
                int age = Integer.parseInt(((EditText)findViewById(R.id.modify_origin)).getText().toString());
                if (age <= 0 || age >= 100)
                    alert(new AlertMessage("请正确输入年龄", ""));
            } catch (Exception ex) {
                alert(new AlertMessage("请正确输入年龄", ""));
            }
        }

        else if (title == "身高(CM)") {
            try {
                int height = Integer.parseInt(((EditText) findViewById(R.id.modify_origin)).getText().toString());
                if (height <= 100 || height >= 250) {
                    alert(new AlertMessage("请正确输入身高", ""));
                }
            } catch (Exception ex) {
                alert(new AlertMessage("请正确输入身高", ""));
            }
        }

        else if (title == "体重(KG)") {
            try {
                int weight = Integer.parseInt(((EditText) findViewById(R.id.modify_origin)).getText().toString());
                if (weight <= 0 || weight >= 500) {
                    alert(new AlertMessage("请正确输入体重", ""));
                }
            } catch (Exception ex) {
                alert(new AlertMessage("请正确输入体重", ""));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        title = getIntent().getStringExtra("title");
        origin = getIntent().getStringExtra("origin");
        ((TextView)findViewById(R.id.modify_title)).setText(title);
        ((TextView)findViewById(R.id.modify_origin)).setText(origin);
        ((TextView)findViewById(R.id.modify_cancel)).setOnClickListener(onClickListener);
        ((TextView)findViewById(R.id.modify_finish)).setOnClickListener(onClickListener);
        ((ImageView)findViewById(R.id.modify_delete)).setOnClickListener(onClickListener);
    }
}