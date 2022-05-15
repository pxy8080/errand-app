package com.erradns.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.erradns.Sophix.R;

public class StartActivity extends BaseActivity {
private ImageView animation;
private AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        animation=findViewById(R.id.animation);
        animation.setBackgroundResource(R.drawable.animation);
        animationDrawable = (AnimationDrawable) animation.getBackground();

        animationDrawable.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(StartActivity.this,
                        HomeActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 500);
    }
}