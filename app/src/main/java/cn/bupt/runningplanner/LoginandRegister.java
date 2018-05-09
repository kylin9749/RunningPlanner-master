package cn.bupt.runningplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import cn.bupt.runningplanner.auth.LoginFragment;
import cn.bupt.runningplanner.classes.HomePage.datadase.DbManager;
import cn.bupt.runningplanner.classes.HomePage.datadase.MysqliteHelper;

public class LoginandRegister extends AppCompatActivity {

    public static MysqliteHelper helper=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginand_register);

        //创建数据库
        if(helper == null){
            helper = new MysqliteHelper(this);
        }
        //helper.getWritableDatabase();

//      helper.getWritableDatabase();

        getSupportFragmentManager().beginTransaction().replace(R.id.usermainfragment,new LoginFragment()).commit();
    }
}
