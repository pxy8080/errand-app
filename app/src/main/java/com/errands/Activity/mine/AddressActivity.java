package com.errands.Activity.mine;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.errands.Activity.BaseActivity;


import com.errands.Adapter.Addressadapter;
import com.errands.DB.AddressDBHelper;
import com.errands.Https.UtilHttp;
import com.errands.Model.Address;
import com.errands.Model.Result;
import com.errands.Sophix.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.FormBody;

public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView address_rv;
    private List<Address> addresses = new ArrayList<>();
    private AlertView add_address_alertView;
    private Button add_address;
    private Addressadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        fetchaddress();

        initView();
        address_rv = findViewById(R.id.address_rv);
        adapter = new Addressadapter(addresses, this);
        System.out.println("addresses"+ JSON.toJSON(addresses));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        address_rv.setLayoutManager(manager);
        address_rv.setAdapter(adapter);
        add_address = findViewById(R.id.add_address);
        add_address.setOnClickListener(this);

    }

    private void initView() {
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("地址");
    }

    private void initAddView() {
        View add_view = getLayoutInflater().inflate(R.layout.layout_add_address, null);
        EditText add_address_input = add_view.findViewById(R.id.add_address_input);
        add_address_alertView = new AlertView("添加地址", null,
                "取消", null, new String[]{"完成"},
                this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (o == add_address_alertView && position != AlertView.CANCELPOSITION) {
                    String address = add_address_input.getText().toString().trim();
                    if (address.isEmpty())
                        showToast("输入为空");
                    else {
                        ProgressDialog dialog = new ProgressDialog(AddressActivity.this);
                        dialog.setMessage("正在修改");
                        dialog.show();
                        insertAddress(account.getId(), address);
                        dialog.dismiss();
                    }
                }
            }
        });
        add_address_alertView.addExtView(add_view);
        add_address_alertView.show();

    }


    private void fetchaddress() {
        //依靠DatabaseHelper带全部参数的构造函数创建数据库
        AddressDBHelper dbHelper = new AddressDBHelper(AddressActivity.this, "address.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("address", new String[]{"id", "user_id", "address"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String user_id = cursor.getString(cursor.getColumnIndex("user_id"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                addresses.add(new Address(id, user_id, address));
            } while (cursor.moveToNext());
            System.out.println("此时的address"+addresses);
        }
        db.close();
    }

    void insertAddress(String user_id, String address) {
        AddressDBHelper dbHelper = new AddressDBHelper(this, "address.db", null, 1);
        //依靠DatabaseHelper带全部参数的构造函数创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String uuid = UUID.randomUUID().toString();    //获取UUID并转化为String对象
        uuid = uuid.replace("-", "");

        FormBody.Builder frombody = new FormBody.Builder();
        frombody.add("id", uuid);
        frombody.add("user_id", user_id);
        frombody.add("address", address);

        UtilHttp utilHttp = new UtilHttp();
        UtilHttp.ICallBack iCallBack = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onFailure: " + throwable);
                showToast("添加失败，请重试");
            }

            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                Result result = new Result();
                result = gson.fromJson(response, new TypeToken<Result>() {
                }.getType());
                Gson gson2 = new Gson();
                Address address = gson2.fromJson(new Gson().toJson(result.getData()), Address.class);
                ContentValues values = new ContentValues();
                values.put("id", address.getId());
                values.put("user_id", address.getUser_id());
                values.put("address", address.getAddress());
                db.insert("address", null, values);
                db.close();
                addresses.add(address);
                adapter.notifyItemInserted(addresses.size());
            }
        };
        try {
            utilHttp.untilPostForm(frombody.build(), "address/addaddress", iCallBack);
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address:
                Log.i(TAG, "onClick: 点击了添加地址");
                initAddView();
                break;
            case R.id.back_img:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}