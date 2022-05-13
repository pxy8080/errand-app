package com.erradns.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.erradns.Model.account;
import com.erradns.Sophix.R;
import com.erradns.Util.CountDownTimerUtils;
import com.erradns.Util.RandomNumber;
import com.erradns.Util.SendEmailUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;


public class BaseActivity extends AppCompatActivity {
    account account = new account();
    private TextView line;
    public static String TAG = "TAG";
    public long verificationCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

//        System.out.println("状态栏高"+ QMUIDisplayHelper.getStatusBarHeight(this));
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);

        getpersonalmessage();
        System.out.println("获取信息"+account.getHeadportrait());
    }

    void getpersonalmessage() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String id = sharedPreferences.getString("user_id", "");
        int phone = sharedPreferences.getInt("user_phone", 0);
        String email = sharedPreferences.getString("user_email", "");
        String password=sharedPreferences.getString("user_password", "");
        String nickname = sharedPreferences.getString("user_nickname", "");
        String headportrait = sharedPreferences.getString("user_headportrait", "");
        String school = sharedPreferences.getString("user_school", "");
        Boolean islogin = sharedPreferences.getBoolean("user_islogin", false);
        account.setId(id);
        account.setPhone(phone);
        account.setEmail(email);
        account.setPassword(password);
        account.setNickname(nickname);
        account.setHeadportrait(headportrait);
        account.setSchool(school);
        account.setIslogin(islogin);
    }


    void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public static void SendPhoneYZM_BT(Context context, Button view) {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                context, view, 60000, 1000);
        if (!CountDownTimerUtils.is_no) {
            mCountDownTimerUtils.start();
        } else {
            Log.i(TAG, "SendPhoneYZM_BT: ");
        }
    }

    //发送验证码
    public void sendVerificationCode(final String email) {
        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        RandomNumber rn = new RandomNumber();
                        verificationCode = rn.getRandomNumber(6);
                        SendEmailUtil se = new SendEmailUtil(email);
                        se.sendHtmlEmail(verificationCode);//发送html邮件
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //存储个人账户信息，后面每个活动都可以获取
    void savepersonalmessage(account account) {
        SharedPreferences pref = getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_id", account.getId());
        editor.putInt("user_phone", account.getPhone());
        editor.putString("user_email", account.getEmail());
        editor.putString("user_password", account.getPassword());
        editor.putString("user_nickname", account.getNickname());
        editor.putString("user_headportrait", account.getHeadportrait());
        editor.putString("user_school", account.getSchool());
        editor.putBoolean("user_islogin", account.getIslogin());
        editor.commit();
    }
}