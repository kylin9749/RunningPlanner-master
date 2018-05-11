package cn.bupt.runningplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BodyActivity extends AppCompatActivity {
    private BodyActivity self = this;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.change_age:
                case R.id.change_height:
                case R.id.change_name:
                case R.id.change_sex:
                case R.id.change_weight:
                    Intent intent = new Intent();
                    intent.putExtra("title", v.getId());
                    intent.putExtra("origin", ((TextView) findViewById(v.getId())).getText());
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

        ((Button) findViewById(R.id.change_age)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.change_height)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.change_name)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.change_sex)).setOnClickListener(onClickListener);
        ((Button) findViewById(R.id.change_weight)).setOnClickListener(onClickListener);
    }
}