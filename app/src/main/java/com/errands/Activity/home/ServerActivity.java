package com.errands.Activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.errands.Activity.HomeActivity;
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
    private RecyclerView recyclerview;
    private String ORDER_TYPE = null;
    private final List<Order> orders = new ArrayList<>();
    private Orderdetail_buy_Adapter orderdetail_buy_adapter;
    private Orderdetail_send_Adapter orderdetail_send_adapter;
    private Orderdetail_take_Adapter orderdetail_take_adapter;
    private String myAddress;
    private EditText remark_input, price_input;
    private AlertView buy_alterview, send_alterview, take_alertView;
    private EditText task_address;

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
        title.setText("????????????");
        TextView nickname = findViewById(R.id.nickname);
        nickname.setText("????????????" + account.getNickname());
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

    //?????????????????????
    private void initSpinner() {
        ArrayAdapter<String> adapterForSpinner;
        fetchaddress();
        adapterForSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, addresses);
        add_select.setAdapter(adapterForSpinner);
        add_select.setSelection(0);
        //???????????????
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
        item.add("??????");
        item.add("??????");
        item.add("??????");
//???????????????????????????
        typeForSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, item);
        type_select.setAdapter(typeForSpinner);
        type_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        orders.clear();//??????????????????????????????order?????????
                        task_type.setImageResource(R.drawable.icon1);//???????????????????????????
                        ORDER_TYPE = "??????";
                        orderdetail_buy_adapter = new Orderdetail_buy_Adapter(orders, ServerActivity.this);
                        LinearLayoutManager manager1 = new LinearLayoutManager(ServerActivity.this);
                        //?????????????????????
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
                        orders.clear();//??????????????????????????????order?????????
                        task_type.setImageResource(R.drawable.icon3);
                        ORDER_TYPE = "??????";
                        orderdetail_send_adapter = new Orderdetail_send_Adapter(orders, ServerActivity.this);
                        LinearLayoutManager manager2 = new LinearLayoutManager(ServerActivity.this);
                        recyclerview.setLayoutManager(manager2);
                        recyclerview.setAdapter(orderdetail_send_adapter);
                        break;
                    case 2:
                        orders.clear();//??????????????????????????????order?????????
                        task_type.setImageResource(R.drawable.icon2);
                        ORDER_TYPE = "??????";
                        orderdetail_take_adapter = new Orderdetail_take_Adapter(orders, ServerActivity.this);
                        LinearLayoutManager manager3 = new LinearLayoutManager(ServerActivity.this);
                        recyclerview.setLayoutManager(manager3);
                        recyclerview.setAdapter(orderdetail_take_adapter);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //???????????????????????????????????????
                type_select.setSelection(0);
            }
        });
    }

    private void fetchaddress() {
        //??????DatabaseHelper?????????????????????????????????????????????
        AddressDBHelper dbHelper = new AddressDBHelper(ServerActivity.this, "address.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("address", new String[]{"id", "User_id", "address"}, null, null, null, null, null);
        System.out.println("???" + cursor.moveToNext());
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
                    case "??????":
                        @SuppressLint("InflateParams") View buyview = getLayoutInflater().inflate(R.layout.buy_alterview, null);
                        final EditText add_description = buyview.findViewById(R.id.add_description);
                        final EditText add_amount = buyview.findViewById(R.id.add_amount);
                        final EditText add_estimation = buyview.findViewById(R.id.add_estimation);
                        final EditText add_name = buyview.findViewById(R.id.add_name);
                        buy_alterview = new AlertView("????????????", null,
                                "??????", null, new String[]{"??????"},
                                this, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                String description = add_description.getText().toString().trim();
                                String amount = add_amount.getText().toString().trim();
                                String estimation = add_estimation.getText().toString().trim();
                                String name = add_name.getText().toString().trim();
                                //????????????????????????
                                if (o == buy_alterview && position != AlertView.CANCELPOSITION) {
                                    if (description.isEmpty() || amount.isEmpty() || estimation.isEmpty() || name.isEmpty()) {
                                        showToast("????????????");
                                    } else {
                                        orders.add(new Order(null, name, description, null, Integer.parseInt(amount), Double.parseDouble(estimation), null));
                                        orderdetail_buy_adapter.notifyItemInserted(orders.size());
                                    }
                                }
                            }
                        });
                        buy_alterview.addExtView(buyview);
                        buy_alterview.show();
                        break;
                    case "??????":
                        @SuppressLint("InflateParams") View sendview = getLayoutInflater().inflate(R.layout.send_alterview, null);
                        final EditText add_description2 = sendview.findViewById(R.id.add_description);
                        send_alterview = new AlertView("????????????", null,
                                "??????", null, new String[]{"??????"},
                                this, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                String description2 = add_description2.getText().toString().trim();
                                //????????????????????????
                                if (o == send_alterview && position != AlertView.CANCELPOSITION) {
                                    if (description2.isEmpty()) {
                                        showToast("????????????");
                                    } else {
                                        orders.add(new Order(null, null, description2, null, 0, 0, null));
                                        orderdetail_send_adapter.notifyItemInserted(orders.size());
                                    }
                                }
                            }
                        });
                        send_alterview.addExtView(sendview);
                        send_alterview.show();
                        break;
                    case "??????":
                        @SuppressLint("InflateParams") View takeview = getLayoutInflater().inflate(R.layout.take_alterview, null);
                        final EditText add_description3 = takeview.findViewById(R.id.add_description);
                        final EditText add_evidence = takeview.findViewById(R.id.add_evidence);

                        take_alertView = new AlertView("????????????", null,
                                "??????", null, new String[]{"??????"},
                                this, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                String description3 = add_description3.getText().toString().trim();
                                String evidence = add_evidence.getText().toString().trim();
                                //????????????????????????
                                if (o == take_alertView && position != AlertView.CANCELPOSITION) {
                                    if (description3.isEmpty() || evidence.isEmpty()) {
                                        showToast("????????????");
                                    } else {
                                        orders.add(new Order(null, null, description3, evidence, 0, 0, null));
                                        orderdetail_take_adapter.notifyItemInserted(orders.size());
                                    }
                                }
                            }
                        });
                        take_alertView.addExtView(takeview);
                        take_alertView.show();
                        break;
                }
                break;
            case R.id.issue:
                String User_id_send = account.getId();
                String taskaddress = task_address.getText().toString().trim();
                String name = remark_input.getText().toString();
                String price = price_input.getText().toString();
                OrderBase orderBase = new OrderBase(null, User_id_send, null, myAddress, taskaddress, ORDER_TYPE, name, 0, null, null, price);
                switch (ORDER_TYPE) {
                    case "??????":
                        order_post(orderBase, orders, "buyorder");
                        break;
                    case "??????":
                        order_post(orderBase, orders, "sendorder");
                        break;
                    case "??????":
                        order_post(orderBase, orders, "takeorder");
                        break;
                    default:
                        break;
                }
                Intent to_home = new Intent(this, HomeActivity.class);
                showToast("??????????????????");
                startActivity(to_home);
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
                System.out.println("buyorder??????" + throwable);
            }

            @Override
            public void onSuccess(String response) {
                System.out.println("buyorder??????" + response);
            }
        };
        try {
            utilHttp.untilPostJson("order/" + port, JSON.toJSON(orderinfo).toString(), iCallBack);
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast("????????????????????????" + e.toString());
                }
            });
        }


    }

    //???????????????????????????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Uri selectedImage = data.getData();
            System.out.println("???????????????" + selectedImage.toString());
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}