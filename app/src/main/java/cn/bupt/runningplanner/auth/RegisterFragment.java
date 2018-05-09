package cn.bupt.runningplanner.auth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import cn.bupt.runningplanner.LoginandRegister;
import cn.bupt.runningplanner.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.bupt.runningplanner.LoginandRegister.helper;


public class RegisterFragment extends Fragment implements View.OnClickListener{

    public RegisterFragment() {
        // Required empty public constructor
    }


    private EditText name;
    private EditText email;
    private EditText password;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        name = (EditText) view.findViewById(R.id.name_register);
        email = (EditText) view.findViewById(R.id.email_register);
        password = (EditText) view.findViewById(R.id.password_register);

        view.findViewById(R.id.signin).setOnClickListener(this);
        view.findViewById(R.id.register).setOnClickListener(this);
        view.findViewById(R.id.fanhui).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.usermainfragment,new LoginFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.register:
                register(name.getText().toString(), email.getText().toString(), password.getText().toString());
                break;
            case R.id.fanhui:
                getFragmentManager().beginTransaction().replace(R.id.usermainfragment, new LoginFragment()).commit();
                break;
        }
    }

    private void register(String namestr, String emailstr, String paswordstr) {
        Boolean is_validate = validate(namestr, emailstr, paswordstr);

        //进行post请求
        if (is_validate) {

            SQLiteDatabase db= helper.getWritableDatabase();

            //获取注册信息
            String mail=((EditText)view.findViewById(R.id.email_register)).getText().toString();
            String username=((EditText)view.findViewById(R.id.name_register)).getText().toString();
            String password=((EditText)view.findViewById(R.id.password_register)).getText().toString();
            db.execSQL("insert into PersonInformation(Email,UserName,Password) values(?,?,?)",new Object[]{mail,username,password});
            //System.out.println("注册成功！");
            db.close();
            Toast.makeText(getContext(), "注册成功!", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().replace(R.id.usermainfragment, new LoginFragment()).commit();



        }
    }

    public boolean validate(String namestr, String emailstr, String paswordstr){
        String rex = "[\\w-]+[\\.\\w]*@[\\w]+(\\.[\\w]{2,3})";
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(emailstr);
        if (namestr.length() < 3 || namestr.length() > 20) {
            name.setError("用户名长度为3~20");
            return false;
        }
        if (m.find() == false) {
            email.setError("邮箱地址不正确");
            return false;
        }
        if (paswordstr.length() < 6 || paswordstr.length() > 15) {
            password.setError("密码长度为6~15");
            return false;
        }
        return true;

    }


}
