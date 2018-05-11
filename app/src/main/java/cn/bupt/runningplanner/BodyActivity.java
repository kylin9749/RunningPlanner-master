package cn.bupt.runningplanner;

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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = null;
            switch (v.getId()) {
                case R.id.change_age:
                    title = "年龄";
                    intent = new Intent();intent.putExtra("title", title);
                    intent.putExtra("origin", "这是内容");
                    intent.setClass(self, ModifyInfoActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.change_height:
                    title = "身高(CM)";
                    intent = new Intent();intent.putExtra("title", title);
                    intent.putExtra("origin", "这是内容");
                    intent.setClass(self, ModifyInfoActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.change_name:
                    title = "昵称";
                    intent = new Intent();intent.putExtra("title", title);
                    intent.putExtra("origin", "这是内容");
                    intent.setClass(self, ModifyInfoActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.change_sex:
                    title = "性别";
                    intent = new Intent();intent.putExtra("title", title);
                    intent.putExtra("origin", "这是内容");
                    intent.setClass(self, ModifySex.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.change_weight:
                    title = "体重(KG)";
                    intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("origin", "这是内容");
                    intent.setClass(self, ModifyInfoActivity.class);
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
        setContentView(R.layout.activity_body);

        ((ImageView)findViewById(R.id.change_age)).setOnClickListener(onClickListener);
        ((ImageView)findViewById(R.id.change_height)).setOnClickListener(onClickListener);
        ((ImageView)findViewById(R.id.change_name)).setOnClickListener(onClickListener);
        ((ImageView)findViewById(R.id.change_sex)).setOnClickListener(onClickListener);
        ((ImageView)findViewById(R.id.change_weight)).setOnClickListener(onClickListener);
    }
}