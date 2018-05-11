package cn.bupt.runningplanner.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.runningplanner.MainActivity;
import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.entity.Result;
import cn.bupt.runningplanner.entity.UserAuthInfo;
import okhttp3.Call;
import okhttp3.Response;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.bupt.runningplanner.LoginandRegister.helper;


public class LoginFragment extends Fragment implements View.OnClickListener{


    public LoginFragment() {
        // Required empty public constructor
    }
    Message message = new Message();
    private EditText emailEdit;
    private EditText passwordEdit;
    private String jsonContext;
    View view = null;
    private ObjectMapper mapper;
    private int MESSAGR = 0;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox passCheckBox;
    private boolean isRemember = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        emailEdit = (EditText) view.findViewById(R.id.email);
        passwordEdit = (EditText) view.findViewById(R.id.password);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        passCheckBox = (CheckBox) view.findViewById(R.id.remember_pass);

        // 未实现
        isRemember = sharedPreferences.getBoolean("remember_password", false);

        if (isRemember) {
            // 将账号和密码都设置到文本框中
            String email = sharedPreferences.getString("email", "");
            String password = sharedPreferences.getString("password", "");
            emailEdit.setText(email);
            passwordEdit.setText(password);
            passCheckBox.setChecked(true);
            login(email, password);
        }
        view.findViewById(R.id.register).setOnClickListener(this);
        view.findViewById(R.id.login).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                getFragmentManager().beginTransaction().replace(R.id.usermainfragment, new RegisterFragment()).commit();
                break;
            case R.id.login:
                login(emailEdit.getText().toString(), passwordEdit.getText().toString());
                break;
        }
    }

    public void login(String emailstr, String passwordstr) {
        Boolean is_validate = validate(emailstr, passwordstr);
        if (is_validate) {

//

            //获取登录信息
            String mail = ((EditText) view.findViewById(R.id.email)).getText().toString();
            String password = ((EditText) view.findViewById(R.id.password)).getText().toString();
            final UserAuthInfo userAuthInfo = new UserAuthInfo();
            userAuthInfo.setMail(mail);
            userAuthInfo.setPassword(password);
            //将登录信息转换成json格式发送个服务器端用以验证
            try {
                mapper = new ObjectMapper();
                jsonContext = mapper.writeValueAsString(userAuthInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //向服务器发送数据请求数据查询的匹配
            HttpUtil.sendOkHttpRequest("http://114.115.154.47:8080/RunningPlanner/auth", jsonContext, new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Looper.prepare();
                    Toast.makeText(getContext(), "网络开小差咯，请检查网络连接", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    MESSAGR = 0;
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Result result = mapper.readValue(response.body().byteStream(), Result.class);

                    if (result.getResultCode() == Result.SUCCESS) {
                        Looper.prepare();
                        Toast.makeText(getContext(), "登录成功!", Toast.LENGTH_SHORT).show();

                        editor = sharedPreferences.edit();

                        // 复选框被选中
                        if (passCheckBox.isChecked()) {
                            editor.putBoolean("remember_password", true);
                            editor.putString("email", userAuthInfo.getMail());
                            editor.putString("password", userAuthInfo.getPassword());
                        } else {
                            editor.clear();
                        }
                        editor.apply();

                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(getContext(), "账号或密码输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
//                    handler.sendMessage(message);
                }

            });
        }
    }


    public boolean validate(String emailstr, String passwordstr){
        String rex = "[\\w-]+[\\.\\w]*@[\\w]+(\\.[\\w]{2,3})";
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(emailstr);
        if (m.find() == false) {
            emailEdit.setError("邮箱地址不正确");
            return false;
        }
        if (passwordstr.length() < 6 || passwordstr.length() > 15) {
            passwordEdit.setError("密码长度为6~15");
            return false;
        }
        return true;
    }

}
