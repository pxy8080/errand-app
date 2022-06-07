package com.errands.Activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.errands.Activity.BaseActivity;
import com.errands.Adapter.GoodsAdapter;
import com.errands.Https.UtilHttp;
import com.errands.Model.Order;
import com.errands.Model.OrderBase;
import com.errands.Model.Result;
import com.errands.Sophix.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * AcceptActivity 接受订单详情Activity
 */
public class AcceptActivity extends BaseActivity implements View.OnClickListener {
    private Handler handler;
    GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        //获取传过来的orderbase信息并解析
        String value = getIntent().getStringExtra("orderbase");
        Gson gson = new Gson();
        OrderBase orderBase = gson.fromJson(value, OrderBase.class);
        initView(orderBase);
    }

    @SuppressLint({"SetTextI18n", "HandlerLeak"})
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
        TextView price = findViewById(R.id.price);
        price.setText("价格：" + orderBase.getPrice());

        //接受任务
        Button accept = findViewById(R.id.accept);
        accept.setOnClickListener(this);

        ListView goodslistview = findViewById(R.id.goodslistview);

        //通过订单id获取物品信息
        fetchgoods(orderBase.getId());

        //接受fetchgoods获取的返回数据
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Gson gson = new Gson();
                List<Order> orders = new ArrayList<>();
                orders = gson.fromJson(JSON.toJSON(msg.obj).toString(),
                        new TypeToken<List<Order>>() {
                        }.getType());
                if (msg.what == 1) {
                    switch (orderBase.getType()) {
                        case "代购":
                            goodsAdapter = new GoodsAdapter(orders, "代购", AcceptActivity.this);
                            goodslistview.setAdapter(goodsAdapter);
                            break;
                        case "代送":
                            goodsAdapter = new GoodsAdapter(orders, "代送", AcceptActivity.this);
                            goodslistview.setAdapter(goodsAdapter);
                            break;
                        case "代取":
                            goodsAdapter = new GoodsAdapter(orders, "代取", AcceptActivity.this);
                            goodslistview.setAdapter(goodsAdapter);
                            break;
                        default:
                            break;
                    }
                }
            }

        };

    }

    //通过orderid获取物品信息
    private void fetchgoods(String id) {
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i(TAG, "onFailure: 拿到订单详情发生错误");
            }

            @Override
            public void onSuccess(String response) {
                //解析成Result并且传result.getData()值给hanlde
                Gson gson1 = new Gson();
                Result result = gson1.fromJson(response, new TypeToken<Result>() {
                }.getType());
                Message message = new Message();
                message.what = 1;
                message.obj = result.getData();
                handler.sendMessage(message);
            }
        };
        try {
            utilHttp.utilGet("order/showAllOrder?id=" + id, callback);
        } catch (Exception e) {
            showToast("拿到订单详情发生错误");
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.accept:

                break;
            default:
                break;
        }

    }
}