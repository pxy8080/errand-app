package com.errands.Activity.mine;
import android.os.Bundle;
import android.view.View;

import com.errands.Activity.BaseActivity;
import com.errands.Sophix.R;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        back_img=findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        title=findViewById(R.id.title);
        title.setText("关于跑腿");

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