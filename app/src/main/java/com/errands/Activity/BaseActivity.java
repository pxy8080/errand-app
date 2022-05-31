package com.errands.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.errands.Model.User;
import com.errands.Sophix.R;
import com.errands.Util.CountDownTimerUtils;
import com.errands.Util.RandomNumber;
import com.errands.Util.SendEmailUtil;


public class BaseActivity extends AppCompatActivity {
    public User account = new User();
    public static String TAG = "TAG";
    public long verificationCode = 0;
    public ImageView back_img;
    public TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        getpersonalmessage();



    }


    void getpersonalmessage() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String id = sharedPreferences.getString("user_id", "");
        String phone = sharedPreferences.getString("user_phone", "");
        String email = sharedPreferences.getString("user_email", "");
        String password = sharedPreferences.getString("user_password", "");
        String nickname = sharedPreferences.getString("user_nickname", "");
        String headportrait = sharedPreferences.getString("user_headportrait", "");
        String school = sharedPreferences.getString("user_school", "");
        account.setId(id);
        account.setphone(phone);
        account.setEmail(email);
        account.setPassword(password);
        account.setNickname(nickname);
        account.setHeadportrait(headportrait);
        account.setSchool(school);
    }

    public void showToast(String s) {
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
    public void savepersonalmessage(User account) {
        SharedPreferences pref = getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_id", account.getId());
        editor.putString("user_phone", account.getphone());
        editor.putString("user_email", account.getEmail());
        editor.putString("user_password", account.getPassword());
        editor.putString("user_nickname", account.getNickname());
        editor.putString("user_headportrait", account.getHeadportrait());
        editor.putString("user_school", account.getSchool());
        editor.commit();
    }
}