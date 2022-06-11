package com.errands.Activity.mine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import com.errands.Activity.BaseActivity;
import com.errands.Activity.HomeActivity;
import com.errands.Activity.LoginActivity;
import com.errands.Fragment.Order_Managerment.Issue_Fragment;
import com.errands.Fragment.Order_Managerment.Receive_Fragment;
import com.errands.Https.UtilHttp;
import com.errands.Model.Order;
import com.errands.Model.OrderBase;
import com.errands.Model.Result;
import com.errands.Model.User;
import com.errands.Sophix.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Order_managerment_Activity extends BaseActivity implements View.OnClickListener {
    private String[] mTitles = new String[]{"我接受的", "我发布的"};
    private Handler handler;
    private List<OrderBase> issue_orders = new ArrayList<>();
    private List<OrderBase> receive_orders = new ArrayList<>();
    //设置Fragment类型集合
    private final ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_managerment);
        initView();
    }

    @SuppressLint("HandlerLeak")
    private void initView() {
        ImageView back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        TextView title = findViewById(R.id.title);
        title.setText("我的订单");

        getorder(account.getId());
        //标题
        TabLayout tablayout = findViewById(R.id.order_managerment_tab);
        tablayout.addTab(tablayout.newTab().setText(mTitles[0]));
        tablayout.addTab(tablayout.newTab().setText(mTitles[1]));
        //左右滑动
        ViewPager order_managerment_vp = findViewById(R.id.order_managerment_vp);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Gson gson = new Gson();
                List<OrderBase> orderBases = new ArrayList<>();
                orderBases = gson.fromJson(JSON.toJSONString(msg.obj), new TypeToken<List<OrderBase>>() {
                }.getType());
                for (int i = 0; i < orderBases.size(); i++) {
                    if (orderBases.get(i).getUser_id_send().equals(account.getId())) {
                        issue_orders.add(orderBases.get(i));
                    }
                    if (orderBases.get(i).getUser_id_receive().equals(account.getId())) {
                        receive_orders.add(orderBases.get(i));
                    }
                }
                //首先创建对应标题数量的类，需要继承Fragment
                //切记Fragment类数量必须与标题数量相匹配
                fragments.add(new Issue_Fragment(issue_orders, Order_managerment_Activity.this));
                fragments.add(new Receive_Fragment(receive_orders, Order_managerment_Activity.this));
                order_managerment_vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @NonNull
                    @Override
                    public Fragment getItem(int position) {
                        return fragments.get(position);//返回页面Fragment集合对应的下标
                    }

                    @Override
                    public int getCount() {
                        return fragments.size();//返回页面的Fragment数量
                    }

                    @Nullable
                    @Override
                    public CharSequence getPageTitle(int position) {
                        return mTitles[position];//返回标题对应的下标
                    }
                });
                //设置TabLayout模式
                tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                tablayout.setTabMode(TabLayout.MODE_FIXED);
                //TabLayout和ViewPager关联
                tablayout.setupWithViewPager(order_managerment_vp);
            }

        };


    }

    void getorder(String id) {
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onFailure: " + throwable);
            }

            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                Result result = gson.fromJson(response, new TypeToken<Result>() {
                }.getType());
                System.out.println("获取到个人订单" + result.getData());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result.getData();
                handler.sendMessage(msg);
            }
        };
        try {
            utilHttp.utilGet("order/showOrderById?Id=" + id, callback);
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
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

