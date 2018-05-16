package cn.bupt.runningplanner.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import cn.bupt.runningplanner.BodyActivity;
import cn.bupt.runningplanner.HistoryActivity;
import cn.bupt.runningplanner.LoginandRegister;
import cn.bupt.runningplanner.R;


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

        SharedPreferences pref = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        textView.setText(pref.getString("name",""));


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
                SharedPreferences.Editor editor = getContext().getSharedPreferences("userInfo",Context.MODE_PRIVATE).edit();
                editor.putBoolean("remember_password", false);
                editor.apply();
                startActivity(new Intent(getContext(),LoginandRegister.class));
                break;
        }
    }
}
