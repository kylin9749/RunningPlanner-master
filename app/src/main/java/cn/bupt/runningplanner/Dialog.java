package cn.bupt.runningplanner;

import android.content.res.Resources;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Properties;

import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.Util.Test;
import cn.bupt.runningplanner.Util.Transform;
import cn.bupt.runningplanner.entity.ManyPicture;
import cn.bupt.runningplanner.entity.Picture;
import okhttp3.Call;
import okhttp3.Response;

public class Dialog extends AppCompatActivity {

    private Transform transform;
    //private Connection connection = null;
    public static Resources res;
    Picture picture4;
    Picture picture5;
    Picture picture6;
    Picture picture7;
    private ManyPicture manyPicture;

    ObjectMapper mapper = null;
    String jsonContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);


        Log.d("MainActivity","111");

        manyPicture = new ManyPicture();
        res=this.getResources();
        transform=new Transform();
        byte[] bytes4=transform.setPicture(R.drawable.news14);
        byte[] bytes5=transform.setPicture(R.drawable.news15);
        byte[] bytes6=transform.setPicture(R.drawable.news16);
        byte[] bytes7=transform.setPicture(R.drawable.news17);
        picture4 = new Picture();
        picture5 = new Picture();
        picture6 = new Picture();
        picture7 = new Picture();

        picture4.setBytes(bytes4);
        picture4.setNumber(1);

        picture5.setBytes(bytes5);
        picture5.setNumber(2);

        picture6.setBytes(bytes6);
        picture6.setNumber(3);

        picture7.setBytes(bytes7);
        picture7.setNumber(4);

        manyPicture.pictures.add(picture4);
       // manyPicture.pictures.add(picture5);
       // manyPicture.pictures.add(picture6);
       // manyPicture.pictures.add(picture7);


        //int a=bytes.length;
       // Log.d("MainActivity", String.valueOf(a));
        Log.d("MainActivity","555");

        try {
            mapper = new ObjectMapper();
            jsonContext =mapper.writeValueAsString(manyPicture);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }




        HttpUtil.sendOkHttpRequest("http://10.128.202.97:8080/picture  ",jsonContext, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


        BitmapDrawable bitmapDrawable=transform.getPicture(bytes4);



        Test test=(Test) findViewById(R.id.Test1);  //框架类对象

        TextView textView=(TextView)findViewById(R.id.title); //标题信息
        //textView.setText("");  //传入标题

        test.setImageResource1(bitmapDrawable);  //传入图片

        test.setImageResource2(R.drawable.news12);

        test.setImageResource3(R.drawable.news13);

        test.setImageResource4(R.drawable.news14);

        test.setImageResource5(R.drawable.news15);



        test.setText1("  愤怒？试试自由搏击高强度心脏机能训练，对排解闷气来说是绝佳选择。忙碌一天后，你用出拳、肘击和踢腿来释放情绪。你会出一身大汗，把所有的不愉快留在体育馆，带着满满的内啡肽离开。只是要记住：把怒火释放到健身锻炼中是好的，但务必确保身体一发出“停止”的指令，大脑就要服从——人们满怀强烈的负面情绪去锻炼时，受伤的几率要比正常时高不少.");
        test.setText2("  亢奋？试试训练营想调动额外的资源挑战自我?请参加那些定期举办的集体项目，甚至一些新概念的训练营，和伙伴们抓起哑铃吧！提供高强度间歇训练和循环训练项目的课程，不仅有益于心血管健康，也有助于增进肌肉力量。这些效果随着时间推移才会显现。事实上，许多人热爱训练营，就是希望感受跟志同道合者一起完成最后一组高难度动作后的那份欢愉.");
        test.setText3("  愉快？试试跑步要疏导体内的正能量，不妨去慢跑,对那些不太喜欢跑步的人来说，你也许会发现内心的乐观态度让自己的双腿增加了活力，使慢跑变得更有乐趣，或至少更容易被接受。跑步已被证明能振作精神、保护心脏，以及提高睡眠质量。高扬的情绪加上肾上腺素的刺激，还有源源不绝的内啡肽，反过来也会进一步延长好心情的持续时间。");
        test.setText4("  压抑？试试瑜伽训练头脑的最好时机之一是在紧张、焦虑和担忧的时候。赶在一天结束前，从精神上、体力上和情感上舒缓身体的最佳方法就是去做瑜伽。这项需要聚精会神的锻炼有助于让你释放压力，从而换取晚间高质量的休息。专注于深度均匀的呼吸,你将重新找回生活的重心。");
        test.setText5("悲伤？试试游泳把自己浸润在平静的蓝色池水中，让思绪随着你喜欢的泳姿悠荡飘逸。当你以自己习惯的速率和节奏游泳,你将有机会在精神上和身体上得到放松。游泳对身体 冲击较小，但也是绝好的锻炼心脏机能的方式。它使你的心率加速，但对你的关节很友好。流动的水既使你感到神清气爽，又使你倍感安逸舒适。有些人甚至主张，游泳有助于缓解抑郁症。");
    }
}


