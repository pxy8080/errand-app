package com.erradns.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.erradns.Sophix.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class MyinfoActivity extends BaseActivity implements View.OnClickListener{
private Toolbar title_bar;
private TextView title;
private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
initView();
    }

    private void initView() {
        title_bar=findViewById(R.id.title_bar);
        title_bar.setY(QMUIStatusBarHelper.getStatusbarHeight(this));
        title=findViewById(R.id.title);
        title.setText("个人中心");
        back=findViewById(R.id.back_img);
        back.setOnClickListener(this);
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