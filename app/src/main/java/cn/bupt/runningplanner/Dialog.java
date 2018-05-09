package cn.bupt.runningplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class Dialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);

        ImageView imageView1 = (ImageView) findViewById(R.id.news11);
        imageView1.setImageResource(R.drawable.news11);
        ImageView imageView2 = (ImageView) findViewById(R.id.news12);
        imageView2.setImageResource(R.drawable.news12);
        ImageView imageView3 = (ImageView) findViewById(R.id.news13);
        imageView3.setImageResource(R.drawable.news13);
        ImageView imageView4 = (ImageView) findViewById(R.id.news14);
        imageView4.setImageResource(R.drawable.news14);
        ImageView imageView5 = (ImageView) findViewById(R.id.news15);
        imageView5.setImageResource(R.drawable.news15);
        ImageView imageView6 = (ImageView) findViewById(R.id.news16);
        imageView6.setImageResource(R.drawable.news16);
        ImageView imageView7 = (ImageView) findViewById(R.id.news17);
        imageView7.setImageResource(R.drawable.news17);

    }
}
