package com.errands.Activity.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.errands.Activity.BaseActivity;
import com.errands.Adapter.Orderdetail_buy_Adapter;
import com.errands.Adapter.Orderdetail_send_Adapter;
import com.errands.Adapter.Orderdetail_take_Adapter;
import com.errands.DB.AddressDBHelper;
import com.errands.Https.UtilHttp;
import com.errands.Model.Order;
import com.errands.Model.OrderBase;
import com.errands.Model.Orderinfo;

import com.errands.Sophix.R;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class ServerActivity extends BaseActivity implements View.OnClickListener {
    private Spinner add_select, type_select;
    private final List<String> addresses = new ArrayList<>();
    private final List<String> item = new ArrayList<>();
    private ImageView task_type;
    private TextView task_address;
    private RecyclerView recyclerview;
    private int ORDER_TYPE;
    private final List<Order> orders = new ArrayList<>();
    private Orderdetail_buy_Adapter orderdetail_buy_adapter;
    private Orderdetail_send_Adapter orderdetail_send_adapter;
    private Orderdetail_take_Adapter orderdetail_take_adapter;
    private String myAddress;
    private EditText remark_input, price_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        initView();
        initData();
    }

    private void initData() {


    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("任务发布");
        TextView nickname = findViewById(R.id.nickname);
        nickname.setText("发布者：" + account.getNickname());
        task_type = findViewById(R.id.task_type);
        TextView phone = findViewById(R.id.phone);
        phone.setText("Phone:" + account.getPhone());
        add_select = findViewById(R.id.add_select);
        type_select = findViewById(R.id.type_select);
        initSpinner();
        ImageView location = findViewById(R.id.location);
        location.setOnClickListener(this);
        recyclerview = findViewById(R.id.goods_recyclerview);
        task_address = findViewById(R.id.task_address);

        ImageView add_goods = findViewById(R.id.add_goods);
        add_goods.setOnClickListener(this);

        Button issue = findViewById(R.id.issue);
        issue.setOnClickListener(this);

        price_input = findViewById(R.id.price_input);

        remark_input = findViewById(R.id.remark_input);


    }

    //初始化地址选择
    private void initSpinner() {
        ArrayAdapter<String> adapterForSpinner;
        fetchaddress();
        adapterForSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, addresses);
        add_select.setAdapter(adapterForSpinner);
        add_select.setSelection(0);
        //地址的选择
        add_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myAddress = addresses.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> typeForSpinner;
        item.add("代购");
        item.add("代送");
        item.add("代取");
//任务类型的选择事件
        typeForSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, item);
        type_select.setAdapter(typeForSpinner);
        type_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        orders.clear();//重新选择类型，所有的order被清空
                        task_type.setImageResource(R.drawable.icon1);//选中类型，图标变化
                        ORDER_TYPE = 0;
                        orderdetail_buy_adapter = new Orderdetail_buy_Adapter(orders, ServerActivity.this);
                        LinearLayoutManager manager1 = new LinearLayoutManager(ServerActivity.this);
                        //图片的点击事件
                        orderdetail_buy_adapter.buy_onclick(new Orderdetail_buy_Adapter.buy_onclick() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intent, 1);
                            }
                        });
                        recyclerview.setLayoutManager(manager1);
                        recyclerview.setAdapter(orderdetail_buy_adapter);
                        break;
                    case 1:
                        orders.clear();//重新选择类型，所有的order被清空
                        task_type.setImageResource(R.drawable.icon3);
                        ORDER_TYPE = 1;
                        orderdetail_send_adapter = new Orderdetail_send_Adapter(orders, ServerActivity.this);
                        LinearLayoutManager manager2 = new LinearLayoutManager(ServerActivity.this);
                        recyclerview.setLayoutManager(manager2);
                        recyclerview.setAdapter(orderdetail_send_adapter);
                        break;
                    case 2:
                        orders.clear();//重新选择类型，所有的order被清空
                        task_type.setImageResource(R.drawable.icon2);
                        ORDER_TYPE = 2;
                        orderdetail_take_adapter = new Orderdetail_take_Adapter(orders, ServerActivity.this);
                        LinearLayoutManager manager3 = new LinearLayoutManager(ServerActivity.this);
                        recyclerview.setLayoutManager(manager3);
                        recyclerview.setAdapter(orderdetail_take_adapter);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //默认显示选择栏的第一个角色
                type_select.setSelection(0);
            }
        });
    }

    private void fetchaddress() {
        //依靠DatabaseHelper带全部参数的构造函数创建数据库
        AddressDBHelper dbHelper = new AddressDBHelper(ServerActivity.this, "address.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("address", new String[]{"id", "User_id", "address"}, null, null, null, null, null);
        System.out.println("值" + cursor.moveToNext());
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String User_id = cursor.getString(cursor.getColumnIndex("user_id"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
            addresses.add(address);
        }
        db.close();
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
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
            case R.id.add_goods:
                switch (ORDER_TYPE) {
                    case 0:
                        @SuppressLint("InflateParams") View buyview = getLayoutInflater().inflate(R.layout.buy_alterview, null);
                        final EditText add_description = buyview.findViewById(R.id.add_description);
                        final EditText add_amount = buyview.findViewById(R.id.add_amount);
                        final EditText add_estimation = buyview.findViewById(R.id.add_estimation);
                        final EditText add_name = buyview.findViewById(R.id.add_name);
                        //判断输入是都为空
                        //                                if (o == buy_alterview && position != AlertView.CANCELPOSITION) {
                        //                                    if (description.isEmpty() && amount.isEmpty() && estimation.isEmpty() && name.isEmpty()) {
                        //                                        showToast("输入是空");
                        //                                    } else {
                        //                                    }
                        //                                }
                        AlertView buy_alterview = new AlertView("添加物品", null,
                                "取消", null, new String[]{"完成"},
                                this, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                String description = add_description.getText().toString().trim();
                                String amount = add_amount.getText().toString().trim();
                                String estimation = add_estimation.getText().toString().trim();
                                String name = add_name.getText().toString().trim();
                                //判断输入是都为空
//                                if (o == buy_alterview && position != AlertView.CANCELPOSITION) {
//                                    if (description.isEmpty() && amount.isEmpty() && estimation.isEmpty() && name.isEmpty()) {
//                                        showToast("输入是空");
//                                    } else {
                                orders.add(new Order(null, name, description, null, Integer.parseInt(amount), Double.parseDouble(estimation), null));
                                orderdetail_buy_adapter.notifyItemInserted(orders.size());
//                                    }
//                                }
                            }
                        });
                        buy_alterview.addExtView(buyview);
                        buy_alterview.show();
                        break;
                    case 1:
                        @SuppressLint("InflateParams") View sendview = getLayoutInflater().inflate(R.layout.send_alterview, null);
                        final EditText add_description2 = sendview.findViewById(R.id.add_description);
                        AlertView send_alterview = new AlertView("添加物品", null,
                                "取消", null, new String[]{"完成"},
                                this, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                String description2 = add_description2.getText().toString().trim();
                                orders.add(new Order(null, null, description2, null, 0, 0, null));
                                orderdetail_send_adapter.notifyItemInserted(orders.size());
                            }
                        });
                        send_alterview.addExtView(sendview);
                        send_alterview.show();
                        break;
                    case 2:
                        @SuppressLint("InflateParams") View takeview = getLayoutInflater().inflate(R.layout.take_alterview, null);
                        final EditText add_description3 = takeview.findViewById(R.id.add_description);
                        final EditText add_evidence = takeview.findViewById(R.id.add_evidence);

                        AlertView take_alertView = new AlertView("添加物品", null,
                                "取消", null, new String[]{"完成"},
                                this, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                String description3 = add_description3.getText().toString().trim();
                                String evidence = add_evidence.getText().toString().trim();
                                orders.add(new Order(null, null, description3, evidence, 0, 0, null));
                                orderdetail_take_adapter.notifyItemInserted(orders.size());
                            }
                        });
                        take_alertView.addExtView(takeview);
                        take_alertView.show();
                        break;
                }
                break;
            case R.id.issue:
                String User_id_send = account.getId();
                System.out.println("myAddress:" + myAddress);
                String task_address = "111";
                String name = remark_input.getText().toString();
                String price = price_input.getText().toString();
                OrderBase orderBase = new OrderBase(null, User_id_send, null, myAddress, task_address, String.valueOf(ORDER_TYPE), name, 0, null, null, price);
                switch (ORDER_TYPE) {
                    case 0:
                        order_post(orderBase, orders, "buyorder");
                        break;
                    case 1:
                        order_post(orderBase, orders, "sendorder");
                        break;
                    case 2:
                        order_post(orderBase, orders, "takeorder");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    void order_post(OrderBase orderBase, List<Order> order, String port) {
        Orderinfo orderinfo = new Orderinfo(orderBase, order);
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack iCallBack = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                System.out.println("buyorder失败" + throwable);
            }

            @Override
            public void onSuccess(String response) {
                System.out.println("buyorder成功" + response);
            }
        };
        try {
            utilHttp.untilPostJson("order/" + port, JSON.toJSON(orderinfo).toString(), iCallBack);
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast("修改失败，请重试" + e.toString());
                }
            });
        }


    }

    //选择图片的返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Uri selectedImage = data.getData();
            System.out.println("选择的路径" + selectedImage.toString());
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}