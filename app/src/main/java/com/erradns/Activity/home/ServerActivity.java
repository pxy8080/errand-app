package com.erradns.Activity.home;


import android.os.Bundle;
import android.view.View;

import com.erradns.Activity.BaseActivity;
import com.erradns.Sophix.R;

public class ServerActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        initView();
    }

    private void initView() {
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        title=findViewById(R.id.title);
        title.setText("代购发布");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}