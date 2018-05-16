package cn.bupt.runningplanner.Util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bupt.runningplanner.R;

public class Test extends LinearLayout {

    private ImageView mImgView1 = null;
    private TextView mTextView1 = null;

    private ImageView mImgView2 = null;
    private TextView mTextView2 = null;


    private ImageView mImgView3 = null;
    private TextView mTextView3 = null;

    private ImageView mImgView5 = null;
    private TextView mTextView5 = null;

    private ImageView mImgView4 = null;
    private TextView mTextView4 = null;

    private ImageView mImgView6 = null;
    private TextView mTextView6 = null;

    private ImageView mImgView7 = null;
    private TextView mTextView7 = null;

    private ImageView mImgView8 = null;
    private TextView mTextView8 = null;


    private Context mContext;

    public  Test(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.text_image_class, this, true);
        mContext = context;
        mImgView1 = (ImageView)findViewById(R.id.news1);
        mTextView1 = (TextView)findViewById(R.id.text1);


        mImgView2 = (ImageView)findViewById(R.id.news2);
        mTextView2 = (TextView)findViewById(R.id.text2);


        mImgView3 = (ImageView)findViewById(R.id.news3);
        mTextView3 = (TextView)findViewById(R.id.text3);

        mImgView4 = (ImageView)findViewById(R.id.news4);
        mTextView4 = (TextView)findViewById(R.id.text4);

        mImgView5 = (ImageView)findViewById(R.id.news5);
        mTextView5 = (TextView)findViewById(R.id.text5);

        mImgView6 = (ImageView)findViewById(R.id.news6);
        mTextView6 = (TextView)findViewById(R.id.text6);

        mImgView7 = (ImageView)findViewById(R.id.news7);
        mTextView7 = (TextView)findViewById(R.id.text7);

        mImgView8 = (ImageView)findViewById(R.id.news8);
        mTextView8 = (TextView)findViewById(R.id.text8);
    }

    public void setImageResource1(BitmapDrawable bitmapDrawable){
        mImgView1.setImageDrawable(bitmapDrawable);
    }

    public void setText1(String str){
        mTextView1.setText(str);
    }

    public void setImageResource2(int resId){
        mImgView2.setImageResource(resId);
    }
    public void setText2(String str){
        mTextView2.setText(str);
    }


    public void setImageResource3(int resId){
        mImgView3.setImageResource(resId);
    }
    public void setText3(String str){
        mTextView3.setText(str);
    }

    public void setImageResource4(int resId){
        mImgView4.setImageResource(resId);
    }
    public void setText4(String str){
        mTextView4.setText(str);
    }

    public void setImageResource5(int resId){
        mImgView5.setImageResource(resId);
    }
    public void setText5(String str){
        mTextView5.setText(str);
    }

    public void setImageResource6(int resId){
        mImgView6.setImageResource(resId);
    }
    public void setText6(String str){
        mTextView6.setText(str);
    }

    public void setImageResource7(int resId){
        mImgView7.setImageResource(resId);
    }
    public void setText7(String str){
        mTextView7.setText(str);
    }

    public void setImageResource8(int resId){
        mImgView8.setImageResource(resId);
    }
    public void setText8(String str){
        mTextView8.setText(str);
    }

}
