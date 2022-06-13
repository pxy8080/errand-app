package com.errands.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.errands.Adapter.Bill_Ry_Adapter;
import com.errands.Https.UtilHttp;
import com.errands.Model.Bill;
import com.errands.Model.Billinfo;
import com.errands.Model.OrderBase;
import com.errands.Model.Result;
import com.errands.Model.User;
import com.errands.Sophix.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class BillRecordActivity extends BaseActivity implements View.OnClickListener {
    private List<Bill> bills = new ArrayList<>();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_record);
        initView();
    }

    @SuppressLint("HandlerLeak")
    private void initView() {
        TextView title = findViewById(R.id.title);
        title.setText("账单记录");
        ImageView back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        RecyclerView bill_recyclerview = findViewById(R.id.bill_recyclerview);
        getbills(account.getId());
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Gson gson = new Gson();
                    List<Billinfo> bills = gson.fromJson(msg.obj.toString(),
                            new TypeToken<List<Billinfo>>() {
                            }.getType());
                    Bill_Ry_Adapter bill_ry_adapter=new Bill_Ry_Adapter(bills);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(BillRecordActivity.this);
                    bill_recyclerview.setLayoutManager(linearLayoutManager);
                    bill_recyclerview.setAdapter(bill_ry_adapter);
                }
            }
        };
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

    void getbills(String id) {
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onFailure: " + throwable);
            }

            @Override
            public void onSuccess(String response) {
                Gson gson1 = new Gson();
                Result result = gson1.fromJson(response, new TypeToken<Result>() {
                }.getType());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = new Gson().toJson(result.getData());
                handler.sendMessage(msg);
            }
        };
        try {
            utilHttp.utilGet("order/billList?id=" + id, callback);
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}