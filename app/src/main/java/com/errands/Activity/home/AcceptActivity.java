package com.errands.Activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.errands.Activity.BaseActivity;
import com.errands.Model.OrderBase;
import com.errands.Sophix.R;
import com.google.gson.Gson;

public class AcceptActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        //获取传过来的orderbase信息
        String value = getIntent().getStringExtra("orderbase");
        Gson gson = new Gson();
        OrderBase orderBase = gson.fromJson(value, OrderBase.class);
        initView(orderBase);
    }

    @SuppressLint("SetTextI18n")
    private void initView(OrderBase orderBase) {
        //title_bar初始化
        ImageView back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        TextView title = findViewById(R.id.title);
        title.setText("接受订单");

        TextView my_address = findViewById(R.id.my_address);
        my_address.setText("收货地址：" + orderBase.getMyAddress());
        TextView task_address = findViewById(R.id.task_address);
        task_address.setText("任务地址：" + orderBase.getTaskAddress());

        ListView goodslistview=findViewById(R.id.goodslistview);
        fetchgoods();
    }

    private void fetchgoods() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_img) {
            onBackPressed();
        }
    }
}