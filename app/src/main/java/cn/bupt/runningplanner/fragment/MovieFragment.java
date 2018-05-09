package cn.bupt.runningplanner.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import cn.bupt.runningplanner.BodyActivity;
import cn.bupt.runningplanner.HistoryActivity;
import cn.bupt.runningplanner.LoginandRegister;
import cn.bupt.runningplanner.MainActivity;
import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.auth.LoginFragment;

import static cn.bupt.runningplanner.LoginandRegister.helper;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements View.OnClickListener {


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        LinearLayout bodyData = (LinearLayout)view.findViewById(R.id.layout_train_body);
        LinearLayout history = (LinearLayout)view.findViewById(R.id.history_list);
        Button button = (Button)view.findViewById(R.id.logout_button);
        TextView textView = (TextView)view.findViewById(R.id.yonghuming_wode);
        String mail ="123456789@qq.com";
        SharedPreferences pref = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        mail= pref.getString("email","123456789@qq.com");
        SQLiteDatabase db = helper.getWritableDatabase();


        Cursor cursor = db.rawQuery("select * from PersonInformation where Email=?", new String[]{mail});
        boolean result =cursor.moveToNext();
        if(result){
            textView.setText(cursor.getString(1));
            cursor.close();
            db.close();
        }

        bodyData.setOnClickListener(this);
        history.setOnClickListener(this);
        button.setOnClickListener(this);


        return  view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_train_body:
                startActivity(new Intent(getContext(),BodyActivity.class));
                break;
            case R.id.history_list:
                startActivity(new Intent(getContext(),HistoryActivity.class));
                break;
            case R.id.logout_button:
                startActivity(new Intent(getContext(),LoginandRegister.class));
                break;
        }
    }
}
