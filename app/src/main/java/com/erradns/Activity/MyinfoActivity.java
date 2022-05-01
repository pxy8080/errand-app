package com.erradns.Activity;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.erradns.Sophix.R;
import com.erradns.Util.CountDownTimerUtils;
import com.erradns.Util.GlideUtil;
import com.erradns.Util.RandomNumber;
import com.erradns.Util.SendEmailUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.io.File;

import io.reactivex.functions.Consumer;


public class MyinfoActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener {
    private Toolbar title_bar;
    private TextView title;
    private ImageView back, icon;
    private RelativeLayout head_icon, nickname_tx, address_tx, phone_tx, update_pwd_tx, email_tx;
    private TextView nickname, address, phone, email;
    private Uri takephoto_uri;
    private AlertView phone_alertView, pwd_alertView, nickname_alertView;
    private long verificationCode = 0;
    private String account_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        initData();
        initView();

    }

    private void initData() {
        account_email = account.getEmail();
    }

    private void initView() {
        title_bar = findViewById(R.id.title_bar);
        title_bar.setY(QMUIStatusBarHelper.getStatusbarHeight(this));
        title = findViewById(R.id.title);
        title.setText("个人中心");
        back = findViewById(R.id.back_img);
        back.setOnClickListener(this);

        head_icon = findViewById(R.id.head_icon);
        head_icon.setOnClickListener(this);
        nickname_tx = findViewById(R.id.nickname_tx);
        nickname_tx.setOnClickListener(this);
        address_tx = findViewById(R.id.address_tx);
        address_tx.setOnClickListener(this);
        phone_tx = findViewById(R.id.phone_tx);
        phone_tx.setOnClickListener(this);
        update_pwd_tx = findViewById(R.id.update_pwd_tx);
        update_pwd_tx.setOnClickListener(this);
        email_tx = findViewById(R.id.email_tx);
        email_tx.setOnClickListener(this);

        nickname = findViewById(R.id.nickname);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        nickname.setText(account.getNickname());
        phone.setText(String.valueOf(account.getPhone()));
        email.setText(account.getEmail());

        icon = findViewById(R.id.icon);
        GlideUtil.loadImageViewSize(this, "https://img1.baidu.com/it/u=2755628084,71849738&fm=253&fmt=auto&app=120&f=JPEG?w=1000&h=563", 30, 30, icon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                System.out.println("拍照地址：" + takephoto_uri);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.head_icon:
                new AlertView("上传头像", null, "取消", null,
                        new String[]{"拍照", "从相册中选择"},
                        this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                RxPermissions permissions = new RxPermissions(MyinfoActivity.this);
                                //申请权限
                                permissions.request(Manifest.permission.CAMERA)
                                        .subscribe(new Consumer<Boolean>() {
                                            //RxJava的观察者模式
                                            @Override
                                            public void accept(Boolean aBoolean) {
                                                if (aBoolean) {
                                                    //接受
                                                    OpenCamera();
                                                } else {
                                                    //拒绝
                                                    Toast.makeText(MyinfoActivity.this, "授权失败，请前往设置里面授权！", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                break;
                            case 1:
                                showToast("打开相册");
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intent, 2);
                                break;
//                            case -1:
//                                showToast("取消");
//                                break;
                            default:
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.nickname_tx:
                View view2 = getLayoutInflater().inflate(R.layout.nickname_dialog, null);
                final TextView old_nickname = view2.findViewById(R.id.old_nickname);
                old_nickname.setText("原昵称：" + account.getPhone());
                final EditText new_nickname = view2.findViewById(R.id.new_nickname);
                nickname_alertView = new AlertView("修改昵称", null,
                        "取消", null, new String[]{"完成"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        showToast("点击了修改昵称" + new_nickname.getText().toString().trim());
                    }
                });
                nickname_alertView.addExtView(view2);
                nickname_alertView.show();
                break;
            case R.id.address_tx:
                showToast("地址");

                break;
            case R.id.phone_tx:
                View view3 = getLayoutInflater().inflate(R.layout.phone_dialog, null);
                final TextView old_phone = view3.findViewById(R.id.old_phone);
                old_phone.setText("原手机号：" + account.getPhone());
                final EditText new_phone = view3.findViewById(R.id.new_phone);
                final EditText phone_dialog_pwd = view3.findViewById(R.id.phone_dialog_pwd);
                phone_alertView = new AlertView("修改手机", null,
                        "取消", null, new String[]{"完成"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        showToast("输入的是" + new_phone.getText().toString().trim() + "\n"
                                + phone_dialog_pwd.getText().toString().trim());
                    }
                });
                phone_alertView.addExtView(view3);
                phone_alertView.show();
                break;
            case R.id.update_pwd_tx:
                View view4 = getLayoutInflater().inflate(R.layout.pwd_dialog, null);
                final EditText dialog_old_pwd = view4.findViewById(R.id.dialog_old_pwd);
                final EditText dialog_new_pwd = view4.findViewById(R.id.dialog_new_pwd);
                pwd_alertView = new AlertView("修改密码", null,
                        "取消", null, new String[]{"完成"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        showToast("输入的是" + dialog_old_pwd.getText().toString().trim() + "\n"
                                + dialog_new_pwd.getText().toString().trim());
                    }
                });
                pwd_alertView.addExtView(view4);
                pwd_alertView.show();
                break;
            case R.id.email_tx:
                View view5 = getLayoutInflater().inflate(R.layout.email_dialog, null);
                final EditText email_update_security_code = view5.findViewById(R.id.email_update_security_code);
                final Button btn_send_security_code = view5.findViewById(R.id.btn_send_security_code);
                final EditText new_email = view5.findViewById(R.id.new_email);


                btn_send_security_code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendPhoneYZM_BT(MyinfoActivity.this, btn_send_security_code);
                        sendVerificationCode(new_email.getText().toString().trim());
                    }
                });
                pwd_alertView = new AlertView("修改邮箱", null,
                        "取消", null, new String[]{"完成"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (email_update_security_code.getText().toString().trim().equals(String.valueOf(verificationCode))) {
                            Log.i("TAG", "onItemClick: 对了", null);
                        } else {
                            Log.i("TAG", "onItemClick:错了", null);
                        }
                    }
                });
                pwd_alertView.addExtView(view5);
                pwd_alertView.show();
                break;
            default:
                break;
        }
    }

    private void OpenCamera() {
        File outputimage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputimage.exists()) {
                outputimage.delete();
            }
            outputimage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            takephoto_uri = FileProvider.getUriForFile(MyinfoActivity.this, "com.erradns.Sophix.provider", outputimage);
        } else {
            takephoto_uri = Uri.fromFile(outputimage);
        }
        try {
            Intent open_camera = new Intent("android.media.action.IMAGE_CAPTURE");
            open_camera.putExtra(MediaStore.EXTRA_OUTPUT, takephoto_uri);
            startActivity(open_camera);
        } catch (Exception e) {
            System.out.println("错误：" + e.toString());
        }
    }


    //
    @Override
    public void onItemClick(Object o, int position) {
        showToast("点击了" + position + o.getClass().toString());
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

    public static void SendPhoneYZM_BT(Context context, Button view) {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                context, view, 60000, 1000);
        if (!CountDownTimerUtils.is_no) {
            mCountDownTimerUtils.start();
        } else {
            Log.i(TAG, "SendPhoneYZM_BT: ");        }
    }
}