package com.errands.Activity;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.errands.Sophix.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{
private Toolbar title_bar;
private ImageView back_img;
private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        initView();
    }

    private void initView() {
        title_bar=findViewById(R.id.title_bar);
        title_bar.setY(QMUIStatusBarHelper.getStatusbarHeight(this));
        back_img=findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        title=findViewById(R.id.title);
        title.setText("忘记密码");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_img:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}