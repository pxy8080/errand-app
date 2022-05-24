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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.errands.Activity.BaseActivity;
import com.errands.DB.AddressDBHelper;
import com.errands.Sophix.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ServerActivity extends BaseActivity implements View.OnClickListener {
    private Spinner add_select, type_select;
    private List<String> addresses = new ArrayList<>();
    private List<String> item = new ArrayList<>();
    private ImageView location, task_pic, task_type;
    private TextView nickname;
    private EditText phone;

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
        title.setText("任务发布");
        nickname = findViewById(R.id.nickname);
        nickname.setText("发布者：" + account.getNickname());
        task_type = findViewById(R.id.task_type);
        phone = findViewById(R.id.phone);
        phone.setText(account.getphone());
        add_select = findViewById(R.id.add_select);
        type_select = findViewById(R.id.type_select);
        task_pic = findViewById(R.id.task_pic);
        task_pic.setOnClickListener(this);

        initSpinner();


        location = findViewById(R.id.location);
        location.setOnClickListener(this);


    }


    //初始化地址选择
    private void initSpinner() {
        ArrayAdapter<String> adapterForSpinner;

        adapterForSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, addresses);
        add_select.setAdapter(adapterForSpinner);
        fetchaddress();
        ArrayAdapter<String> typeForSpinner;
        item.add("代购");
        item.add("代取");
        item.add("代送");
        item.add("服务");
        typeForSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, item);
        type_select.setAdapter(typeForSpinner);
        type_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        task_type.setImageResource(R.drawable.icon1);
//                        task_type.setBackgroundColor(getResources().getColor(R.color.service_bg1));
                        break;
                    case 1:
                        task_type.setImageResource(R.drawable.icon2);
//                        task_type.setBackgroundColor(getResources().getColor(R.color.service_bg2));
                        break;
                    case 2:
                        task_type.setImageResource(R.drawable.icon3);
//                        task_type.setBackgroundColor(getResources().getColor(R.color.service_bg3));
                        break;
                    case 3:
                        task_type.setImageResource(R.drawable.icon4);
//                        task_type.setBackgroundColor(getResources().getColor(R.color.service_bg4));
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
        Cursor cursor = db.query("address", new String[]{"id", "User_id", "address"}, null, null, null, null, null);
        System.out.println("值" + cursor.moveToNext());
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String User_id = cursor.getString(cursor.getColumnIndex("User_id"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
            addresses.add(address);
        }
        db.close();
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
            case R.id.task_pic:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage));
                    task_pic.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }
}