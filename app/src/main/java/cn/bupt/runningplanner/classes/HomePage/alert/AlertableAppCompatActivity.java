package cn.bupt.runningplanner.classes.HomePage.alert;

import android.support.v7.app.AppCompatActivity;

abstract public class AlertableAppCompatActivity extends AppCompatActivity {
    public void alert(AlertMessage alertMessage) {
        new android.app.AlertDialog.Builder(this).setTitle(alertMessage.getTitle())
                .setMessage(alertMessage.getDetail())
                .show();
    }
}