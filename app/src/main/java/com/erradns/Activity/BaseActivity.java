package com.erradns.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.erradns.Model.User;
import com.erradns.Sophix.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class BaseActivity extends AppCompatActivity {
    User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

//        System.out.println("状态栏高"+ QMUIDisplayHelper.getStatusBarHeight(this));
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);

    }

    void getpersonalmessage() {
        SharedPreferences sharedPreferences = getSharedPreferences("personalmessage",MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        int phone = sharedPreferences.getInt("phone",0);
        String email=sharedPreferences.getString("email","");
        String nickname=sharedPreferences.getString("nickname","");
        String headportrait=sharedPreferences.getString("headportrait","");
        String school=sharedPreferences.getString("school","");
        Boolean islogin=sharedPreferences.getBoolean("islogin",false);
        sharedPreferences.getBoolean("married",false);
        user.setId(id);
        user.setPhone(phone);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setHeadportrait(headportrait);
        user.setSchool(school);
        user.setIslogin(islogin);
    }
    void showToast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}