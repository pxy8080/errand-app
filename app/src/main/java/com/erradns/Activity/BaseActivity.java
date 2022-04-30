package com.erradns.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.erradns.Model.account;
import com.erradns.Sophix.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;


public class BaseActivity extends AppCompatActivity {
    account account = new account();
    private TextView line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

//        System.out.println("状态栏高"+ QMUIDisplayHelper.getStatusBarHeight(this));
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);

        getpersonalmessage();
    }

    void getpersonalmessage() {
        SharedPreferences sharedPreferences =getSharedPreferences("userinfo", MODE_PRIVATE);
        String id = sharedPreferences.getString("user_id", "");
        int phone = sharedPreferences.getInt("user_phone", 0);
        String email = sharedPreferences.getString("user_email", "");
        String nickname = sharedPreferences.getString("use_nickname", "");
        String headportrait = sharedPreferences.getString("use_headportrait", "");
        String school = sharedPreferences.getString("user_school", "");
        Boolean islogin = sharedPreferences.getBoolean("use_islogin", false);
        account.setId(id);
        account.setPhone(phone);
        account.setEmail(email);
        account.setNickname(nickname);
        account.setHeadportrait(headportrait);
        account.setSchool(school);
        account.setIslogin(islogin);
        System.out.println("aaaaa"+ account.getId());
        System.out.println("aaaaa"+ account.getIslogin());
    }


    void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}