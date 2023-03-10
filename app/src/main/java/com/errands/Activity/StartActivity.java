package com.errands.Activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.errands.DB.AddressDBHelper;
import com.errands.Https.UtilHttp;
import com.errands.Model.Address;
import com.errands.Model.Result;
import com.errands.Sophix.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends BaseActivity {
    private final List<Address> addresses = new ArrayList<>();
    private Result result = new Result();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageView animation = findViewById(R.id.animation);
        animation.setBackgroundResource(R.drawable.animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) animation.getBackground();

        animationDrawable.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(StartActivity.this,
                        HomeActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 500);


        AddressDBHelper dbHelper = new AddressDBHelper(StartActivity.this, "address.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("address", new String[]{"id", "user_id", "address"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            fetchaddress();
        } else {
            getaddresses();
            fetchaddress();
        }


    }

    private void getaddresses() {
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onFailure: " + throwable);
            }

            @Override
            public void onSuccess(String response) {
                List<Address> address = new ArrayList<>();
                Gson gson1 = new Gson();
                result = gson1.fromJson(response, Result.class);
                Gson gson2 = new Gson();
                if (result.getCode() == 100) {
                    address = gson2.fromJson(new Gson().toJson(result.getData()),
                            new TypeToken<List<Address>>() {
                            }.getType());
                    saveaddress(address);
                } else {
                    Toast.makeText(StartActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        };
        try {
            utilHttp.utilGet("address/showAddressById?Id="+account.getId(), callback);
        } catch (Exception e) {
            Log.i(TAG, "getaddresses: " + e.toString());
        }
    }

    private void saveaddress(List<Address> address) {
        //??????DatabaseHelper?????????????????????????????????????????????
        AddressDBHelper dbHelper = new AddressDBHelper(StartActivity.this, "address.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int i = 0; i < address.size(); i++) {
            values.put("id", address.get(i).getId());
            values.put("user_id", address.get(i).getUser_id());
            values.put("address", address.get(i).getAddress());
            db.insert("address", null, values);
        }
        db.close();
    }

    void fetchaddress() {
        //??????DatabaseHelper?????????????????????????????????????????????
        AddressDBHelper dbHelper = new AddressDBHelper(StartActivity.this, "address.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("address", new String[]{"id", "user_id", "address"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String user_id = cursor.getString(cursor.getColumnIndex("user_id"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
            addresses.add(new Address(id, user_id, address));
        }
        db.close();
    }
}