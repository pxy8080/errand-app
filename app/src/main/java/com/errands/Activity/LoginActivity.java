package com.errands.Activity;

import androidx.percentlayout.widget.PercentLayoutHelper;
import androidx.percentlayout.widget.PercentRelativeLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.errands.Https.UtilHttp;
import com.errands.Model.Result;
import com.errands.Model.User;
import com.errands.Sophix.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import okhttp3.FormBody;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private boolean isSigninScreen = true;
    private Spinner school_choose;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button regiseter_send_code;


    private TextInputEditText login_email, login_pwd, register_email, register_pwd, register_nickname, register_phone, register_code_input;
    private CheckBox remember_pwd;
    private Button login, register;

    private boolean issave = false;//是否记住密码
    private Result result = new Result();
    private User account = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();
        initData();


    }

    private void initView() {
        llSignin = findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        llSignup = findViewById(R.id.llSignup);
        llSignup.setOnClickListener(this);
        tvSignupInvoker = findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = findViewById(R.id.tvSigninInvoker);

        login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        register = findViewById(R.id.btn_register);
        register.setOnClickListener(this);

        //邮箱登录输入
        login_email = findViewById(R.id.login_email_input);
        login_pwd = findViewById(R.id.login_pwd_input);
        register_email = findViewById(R.id.register_email_input);
        register_pwd = findViewById(R.id.register_pwd_input);
        register_nickname = findViewById(R.id.register_nickname_input);
        register_phone = findViewById(R.id.register_phone_input);
        remember_pwd = findViewById(R.id.remember_pwd);
        //忘记密码
        TextView login_forget_pwd = findViewById(R.id.login_forget_pwd);
        login_forget_pwd.setOnClickListener(this);
        //选择学校
        school_choose = findViewById(R.id.school_choose);
        //验证码输入
        register_code_input = findViewById(R.id.register_code_input);

        //发送验证码按钮
        regiseter_send_code = findViewById(R.id.regiseter_send_code);

        regiseter_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showToast("点击了发送");
                SendPhoneYZM_BT(LoginActivity.this, regiseter_send_code);
                sendVerificationCode(register_email.getText().toString().trim());
            }
        });
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

        remember_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (remember_pwd.isChecked()) {
                    issave = true;
                } else {
                    issave = false;
                }
            }
        });


    }

    //显示登录模块
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
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        login.startAnimation(clockwise);

    }

    //显示注册模块
    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        register.startAnimation(clockwise);
    }


    //记住账号密码
    void savemessage(boolean a) {
        String email, password;
        @SuppressLint("WrongConstant") SharedPreferences sp = getSharedPreferences("message", MODE_ENABLE_WRITE_AHEAD_LOGGING);
        if (a) {
            email = login_email.getText().toString().trim();
            password = login_pwd.getText().toString().trim();
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("email", email);
            edit.putString("password", password);
            edit.putBoolean("remember_pwd", true);
            boolean commit = edit.commit();
        } else {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("email", "");
            edit.putString("password", "");
            boolean commit = edit.commit();
            edit.putBoolean("remember_pwd", false);
        }
    }

    void initData() {
        //获取输入框上次存储的账号密码
        SharedPreferences sp = getSharedPreferences("message", MODE_PRIVATE);
        String saveemail = sp.getString("email", "");
        String savepwd = sp.getString("password", "");
        Boolean a = sp.getBoolean("remember_pwd", false);
        login_email.setText(saveemail);
        login_pwd.setText(savepwd);
        remember_pwd.setChecked(a);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                ProgressDialog dialog2 = new ProgressDialog(this);
                dialog2.setMessage("正在登录");
                dialog2.show();
                String s1 = login_email.getText().toString().trim();
                String s2 = login_pwd.getText().toString().trim();
                savemessage(issave);
                UtilHttp utilHttp = UtilHttp.obtain();
                UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
                    @Override
                    public void onFailure(String throwable) {
                        Log.i("TAG", "onFailure: " + throwable);
                    }

                    @Override
                    public void onSuccess(String response) {
                        Gson gson1 = new Gson();
                        result = gson1.fromJson(response, new TypeToken<Result>() {
                        }.getType());
                        Gson gson2 = new Gson();
                        if (result.getCode() == 100) {
                            account = gson2.fromJson(new Gson().toJson(result.getData()),
                                    new TypeToken<User>() {
                            }.getType());
                            dialog2.dismiss();
                            Intent home_intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(home_intent);
                        } else {
                            dialog2.dismiss();
                            showToast("账号密码错误，请重新输入");
                        }
                        savepersonalmessage(account);

                    }
                };
                try {
                    utilHttp.utilGet("user/getUserinfobyemail?email=" + s1 + "&password=" + s2, callback);
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

                break;

            case R.id.btn_register:
                if (register_code_input.getText().toString().trim().equals(String.valueOf(verificationCode))) {
                    ProgressDialog dialog = new ProgressDialog(this);
                    dialog.setMessage("正在注册");
                    dialog.show();
                    String s3 = register_email.getText().toString().trim();
                    String s4 = register_phone.getText().toString().trim();
                    String s5 = register_pwd.getText().toString().trim();
                    String s6 = register_nickname.getText().toString().trim();
                    String s7 = school_choose.getSelectedItem().toString();
                    User user=new User(null,s4,s3,s5,s6,"http://81.71.163.138:8080/examples/test/initlogo.jpg",s7,0);
                    UtilHttp utilHttp2 = UtilHttp.obtain();
                    UtilHttp.ICallBack callback2 = new UtilHttp.ICallBack() {
                        @Override
                        public void onFailure(String throwable) {
                            Log.i("TAG", "onFailure: " + throwable);
                            showToast(throwable);
                        }

                        @Override
                        public void onSuccess(String response) {
                            Gson gson3 = new Gson();
                            result = gson3.fromJson(response, new TypeToken<Result>() {
                            }.getType());
                            dialog.dismiss();
                            showToast(result.getMessage() + ",请返回登录界面登录");
                        }

                    };
                    try {
                        utilHttp2.untilPostJson("user/adduser",JSON.toJSON(user).toString(),  callback2);
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("注册失败，请重试" + e.toString());
                            }
                        });
                    }
                } else {
                    showToast("验证码错误");
                }

                break;
            case R.id.login_forget_pwd:
                Intent forgetpwd_intent = new Intent(this, ForgetPwdActivity.class);
                startActivity(forgetpwd_intent);
                break;
//            case R.id.regiseter_send_code:
//                showToast("点击发送");
//                SendPhoneYZM_BT(LoginActivity.this, regiseter_send_code);
//                sendVerificationCode(register_email.getText().toString().trim());
//                break;

            default:
                break;

        }
    }


    //存储个人账户信息，后面每个活动都可以获取
    public void savepersonalmessage(User account) {
        SharedPreferences pref = getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_id", account.getId());
        editor.putString("user_phone", account.getPhone());
        editor.putString("user_email", account.getEmail());
        editor.putString("user_password", account.getPassword());
        editor.putString("user_nickname", account.getNickname());
        editor.putString("user_headportrait", account.getHeadportrait());
        editor.putString("user_school", account.getSchool());
        editor.commit();
    }



}

