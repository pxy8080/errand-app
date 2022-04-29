package com.erradns.Activity;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.erradns.Sophix.R;
import com.erradns.Util.ScreenSizeUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import org.w3c.dom.Text;

import me.shaohui.bottomdialog.BottomDialog;

public class MyinfoActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar title_bar;
    private TextView title;
    private ImageView back;
    private RelativeLayout head_icon,nickname_tx,address_tx,phone_tx,update_pwd_tx,email_tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        initView();
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
        nickname_tx=findViewById(R.id.nickname_tx);
        nickname_tx.setOnClickListener(this);
        address_tx=findViewById(R.id.address_tx);
        address_tx.setOnClickListener(this);
        phone_tx=findViewById(R.id.phone_tx);
        phone_tx.setOnClickListener(this);
        update_pwd_tx=findViewById(R.id.update_pwd_tx);
        update_pwd_tx.setOnClickListener(this);
        email_tx=findViewById(R.id.email_tx);
        email_tx.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.head_icon:
                Dialog dialog = new Dialog(this, R.style.QMUI);
                View view1 = View.inflate(this, R.layout.choose_photo, null);
                dialog.setContentView(view1);
                dialog.setCanceledOnTouchOutside(true);
                view1.setMinimumHeight((int) (ScreenSizeUtils.getInstance(this).getScreenHeight() * 0.13f));
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = (int) (ScreenSizeUtils.getInstance(this).getScreenWidth() * 0.9f);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;
                dialogWindow.setAttributes(lp);
                dialog.show();
                dialog.getWindow().findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showToast("打开相机");
                    }
                });
                dialog.getWindow().findViewById(R.id.Photo_album).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showToast("打开相册");
                    }
                });
                break;
            case R.id.nickname_tx:
                View view2 = getLayoutInflater().inflate(R.layout.nickname_dialog, null);
                final EditText editText = (EditText) view2.findViewById(R.id.nickname_dialog);
                final TextView old_nickname=view2.findViewById(R.id.old_nickname);
                old_nickname.setText("原昵称:"+"hdsajhd");
                AlertDialog dialog2 = new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.logo)//设置标题的图片
                        .setTitle("输入新昵称")//设置对话框的标题
                        .setView(view2)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content = editText.getText().toString();
                                Toast.makeText(MyinfoActivity.this, content, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).create();
                dialog2.show();
                break;
            case R.id.address_tx:
                showToast("地址");
                break;
            case R.id.phone_tx:
                showToast("手机号");
                break;
            case R.id.update_pwd_tx:
                showToast("更改密码");
                break;
            case R.id.email_tx:
                showToast("更改邮箱");
                break;
            default:
                break;
        }
    }
}