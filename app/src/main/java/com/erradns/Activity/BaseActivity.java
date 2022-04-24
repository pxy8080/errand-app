package com.erradns.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.erradns.sophix.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

//        System.out.println("状态栏高"+ QMUIDisplayHelper.getStatusBarHeight(this));
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
}