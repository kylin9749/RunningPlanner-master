package cn.bupt.runningplanner.auth;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.runningplanner.MainActivity;
import cn.bupt.runningplanner.R;
import cn.bupt.runningplanner.Util.HttpUtil;
import cn.bupt.runningplanner.entity.Result;
import cn.bupt.runningplanner.entity.UserRegisterInfo;
import okhttp3.Call;
import okhttp3.Response;

import org.json.JSONObject;

import java.io.IOException;
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

    private String jsonContext;
    private ObjectMapper mapper;
    private void register(String namestr, String emailstr, String paswordstr) {
        Boolean is_validate = validate(namestr, emailstr, paswordstr);

        //进行post请求
        if (is_validate) {


            //获取注册信息
            UserRegisterInfo userRegisterInfo = new UserRegisterInfo();
            userRegisterInfo.setEmail(((EditText)view.findViewById(R.id.email_register)).getText().toString());
            userRegisterInfo.setName(((EditText)view.findViewById(R.id.name_register)).getText().toString());
            userRegisterInfo.setPassword(((EditText)view.findViewById(R.id.password_register)).getText().toString());

            //将注册信息转换成json格式发送个服务器端用以验证
            try {
                mapper = new ObjectMapper();
                jsonContext = mapper.writeValueAsString(userRegisterInfo);
            }catch (Exception e){
                e.printStackTrace();
            }
            HttpUtil.sendOkHttpRequest("http://10.128.202.97:8080/register",jsonContext,new okhttp3.Callback(){

                @Override
                public void onFailure(Call call, IOException e) {
                    Looper.prepare();
                    Toast.makeText(getContext(),"网络开小差咯，请检查网络连接",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Result result = mapper.readValue(response.body().byteStream(), Result.class);
                    if (result.getResultCode() == Result.SUCCESS) {
                        Looper.prepare();
                        Toast.makeText(getContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.usermainfragment, new LoginFragment()).commit();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(getContext(), "当前邮箱已被注册，请重新输入", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            });





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
