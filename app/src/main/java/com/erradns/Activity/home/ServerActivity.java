package com.erradns.Activity.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.erradns.Activity.BaseActivity;
import com.erradns.Activity.MainActivity;
import com.erradns.Sophix.R;

import java.util.ArrayList;
import java.util.List;

public class ServerActivity extends BaseActivity implements View.OnClickListener {
    private Spinner add_select;
    private List<String> address = new ArrayList<>();
    private ImageView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        initView();
    }

    private void initView() {
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("代购发布");

        add_select = findViewById(R.id.add_select);

        initSpinner();


        location = findViewById(R.id.location);
        location.setOnClickListener(this);


    }

    //初始化地址选择
    private void initSpinner() {
        ArrayAdapter<String> adapterForSpinner;
        address.add("地址一");
        address.add("地址二");
        address.add("地址三");
        address.add("地址四");
        adapterForSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, address);
        ;
        add_select.setAdapter(adapterForSpinner);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.location:
                Intent to_location = new Intent(this, LocationActivity.class);
                startActivity(to_location);
                break;
            default:
                break;
        }
    }
}