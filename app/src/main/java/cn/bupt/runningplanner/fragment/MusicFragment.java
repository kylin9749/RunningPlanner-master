package cn.bupt.runningplanner.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.bupt.runningplanner.MainActivity;
import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.RunningPreparationActivity;

import static cn.bupt.runningplanner.LoginandRegister.helper;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment implements View.OnClickListener {


    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        CardView cardView = (CardView)view.findViewById(R.id.card1);
        cardView.setCardBackgroundColor(Color.argb(0,0,0,0));

        String mail ="123456789@qq.com";
        SharedPreferences pref = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        mail= pref.getString("email","123456789@qq.com");
        SQLiteDatabase db = helper.getWritableDatabase();
        TextView total_mails = (TextView)view.findViewById(R.id.totalmails);
        Cursor cursor = db.rawQuery("select * from PersonInformation where Email=?", new String[]{mail});
        boolean result =cursor.moveToNext();
        if(result){
            double currentLong = cursor.getDouble(8)/1000;
            total_mails.setText(new Double(currentLong).toString());
            db.close();
        }

        Button button = (Button)view.findViewById(R.id.run);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), RunningPreparationActivity.class));
    }
}
