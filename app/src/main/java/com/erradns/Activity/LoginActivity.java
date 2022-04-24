package com.erradns.Activity;

import androidx.percentlayout.widget.PercentLayoutHelper;
import androidx.percentlayout.widget.PercentRelativeLayout;

import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erradns.sophix.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;


    private TextInputEditText login_email,login_pwd,register_email,register_pwd,register_nickname,register_phone;
    private CheckBox remember_pwd;
    private Button login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();


    }

    private void initView() {
        llSignin =  findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        llSignup =findViewById(R.id.llSignup);
        llSignup.setOnClickListener(this);
        tvSignupInvoker = findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker =  findViewById(R.id.tvSigninInvoker);



        login=findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        register=findViewById(R.id.btn_register);
        register.setOnClickListener(this);

        login_email=findViewById(R.id.login_email_input);
        login_pwd=findViewById(R.id.login_pwd_input);
        register_email=findViewById(R.id.register_email_input);
        register_pwd=findViewById(R.id.register_pwd_input);
        register_nickname=findViewById(R.id.register_nickname_input);
        register_phone=findViewById(R.id.register_phone_input);
        remember_pwd=findViewById(R.id.remember_pwd);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });
        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });
        showSigninForm();


    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        login.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        register.startAnimation(clockwise);
    }

    void showToast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String s1=login_email.getText().toString().trim();
                String s2=login_pwd.getText().toString().trim();
                showToast("click login！");
                break;
            case R.id.btn_register:
                String s3=register_email.getText().toString().trim();
                String s4=register_phone.getText().toString().trim();
                String s5=register_pwd.getText().toString().trim();
                String s6=register_nickname.getText().toString().trim();
                showToast("click register");
                break;
            case R.id.remember_pwd:
                break;

            default:
                break;

        }
    }
}

