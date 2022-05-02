package com.erradns.Activity;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.erradns.Https.UtilHttp;
import com.erradns.Model.Result;
import com.erradns.Sophix.R;
import com.erradns.Util.CountDownTimerUtils;
import com.erradns.Util.GlideUtil;
import com.erradns.Util.RandomNumber;
import com.erradns.Util.SendEmailUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.io.File;

import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

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
    private Result result = new Result();

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
        GlideUtil.loadImageViewSize(this, account.getHeadportrait(), 50, 50, icon);
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
                /**
                 * view5 email更改对话框布局
                 */
                View view5 = getLayoutInflater().inflate(R.layout.email_dialog, null);
                final EditText email_update_security_code = view5.findViewById(R.id.email_update_security_code);
                final Button btn_send_security_code = view5.findViewById(R.id.btn_send_security_code);
                final EditText new_email = view5.findViewById(R.id.new_email);

                //发送验证码按钮点击
                btn_send_security_code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //按钮不可选中
                        SendPhoneYZM_BT(MyinfoActivity.this, btn_send_security_code);
                        /**
                         * new_email  输入的新验证码
                         */
                        sendVerificationCode(new_email.getText().toString().trim());
                    }
                });
                //对话框弹窗
                pwd_alertView = new AlertView("修改邮箱", null,
                        "取消", null, new String[]{"完成"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    /**
                     * 点击完成事件
                     * email_update_security_code 输入的验证码
                     * verificationCode 生成的验证码
                     * updateemail post接口更改邮箱
                     */
                    public void onItemClick(Object o, int position) {
                        if (email_update_security_code.getText().toString().trim().equals(String.valueOf(verificationCode))) {
                            updateemail(account.getId(),String.valueOf(account.getPhone()),new_email.getText().toString().trim(),account.getPassword(),
                                    account.getNickname(),account.getHeadportrait(),account.getSchool());
                        } else {
                            showToast("验证码错误，重新验证！");
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

    /**
     * 调用相机
     */
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

    /**
     *
     * @param email 发送邮箱对象
     * verificationCode  生成随机验证码
     */
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

    /**
     *
     * @param context 上下文对象
     * @param view button对象，不可选中的按钮
     */
    public static void SendPhoneYZM_BT(Context context, Button view) {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                context, view, 60000, 1000);
        if (!CountDownTimerUtils.is_no) {
            mCountDownTimerUtils.start();
        } else {
            Log.i(TAG, "SendPhoneYZM_BT: ");
        }
    }

    /**
     *
     * @param s1 id
     * @param s2 手机号
     * @param s3 email
     * @param s4 pwd
     * @param s5 nickname
     * @param s6 headportrait
     * @param s7 school
     */
    void updateemail(String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("正在修改");
        dialog.show();
        FormBody.Builder frombody = new FormBody.Builder();
        frombody.add("id", s1);
        frombody.add("phone", s2);
        frombody.add("email", s3);
        frombody.add("password", s4);
        frombody.add("nickname", s5);
        frombody.add("headportrait", s6);
        frombody.add("school", s7);
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
//                        Log.i("TAG", "onSuccess: " + result.getMessage(), null);
                dialog.dismiss();
                showToast(result.getMessage() );
            }

        };
        try {
            utilHttp2.untilPostForm(frombody.build(), "user/updateuser", callback2);
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast("修改失败，请重试" + e.toString());
                }
            });
        }
    }
}