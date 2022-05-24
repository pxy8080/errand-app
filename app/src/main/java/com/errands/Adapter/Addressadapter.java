package com.errands.Adapter;

import static com.errands.Activity.BaseActivity.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.errands.DB.AddressDBHelper;
import com.errands.Https.UtilHttp;
import com.errands.Model.Address;
import com.errands.Sophix.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

public class Addressadapter extends RecyclerView.Adapter<Addressadapter.MyHolder> implements View.OnClickListener {
    private List<Address> address = new ArrayList<>();
    private Context mcontext;
    AlertView alertview;

    public Addressadapter(List<Address> addresses, Context context) {
        this.mcontext = context;
        this.address = addresses;
    }

    @NonNull
    @Override
    public Addressadapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address, parent, false);
        Addressadapter.MyHolder myHolder = new Addressadapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Addressadapter.MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.address_tv.setText(address.get(position).getAddress());


        holder.update_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View updateview = View.inflate(mcontext, R.layout.layout_add_address, null);
                EditText updatetext = updateview.findViewById(R.id.add_address_input);
                updatetext.setText(address.get(position).getAddress());
                alertview = new AlertView("修改地址", null,
                        "取消", null, new String[]{"完成"},
                        mcontext, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (o == alertview && position != AlertView.CANCELPOSITION) {
                            String updateaddress = updatetext.getText().toString().trim();
                            updateAddress(address.get(position).getId(), address.get(position).getUser_id(), updateaddress);
                            updateList(position, updateaddress);
                        }
                    }
                });
                alertview.addExtView(updateview);
                alertview.show();
                Log.i("TAG", "onClick: 点击了修改" + position);

            }
        });
        holder.delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAddress(address.get(position).getId());
                removeList(position);
            }
        });
    }

    //删除其中一个地址，ui更新
    public void removeList(int position) {
        address.remove(position);//删除数据源,移除集合中当前下标的数据
        notifyItemRemoved(position);//刷新被删除的地方
        notifyItemRangeChanged(position, getItemCount()); //刷新被删除数据，以及其后面的数据
    }

    //更新其中一个地址，ui更新
    public void updateList(int position, String newaddress) {
        address.get(position).setAddress(newaddress);
        notifyItemRemoved(position);//刷新被更新的地方
        notifyItemRangeChanged(position, getItemCount()); //刷新被更新数据，以及其后面的数据
    }

    //更新其中一个地址，ui更新
    public void addList(int position, String newaddress) {
        address.get(position).setAddress(newaddress);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount()); //刷新被更新数据，以及其后面的数据
    }


    private void updateAddress(String id, String User_id, String newaddress) {
        ProgressDialog dialog = new ProgressDialog(mcontext);
        dialog.setMessage("正在修改");
        dialog.show();

        //数据库修改
        AddressDBHelper dbHelper = new AddressDBHelper(mcontext, "address.db", null, 1);
        // 通过DBHelper类获取一个读写的SQLiteDatabase对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 创建ContentValue设置参数
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", newaddress);
        // 修改数据
        // 参数1：tablename
        // 参数2：修改的值
        // 参数3：修改的条件（SQL where语句）
        // 参数4：表示whereClause语句中的表达式的占位符参数列表，这些字符串会替换where条件中?
        db.update("address", contentValues, "id=?", new String[]{id});
        // 释放连接
        db.close();

        //请求服务器修改后台地址数据
        FormBody.Builder frombody = new FormBody.Builder();
        frombody.add("id", id);
        frombody.add("User_id", User_id);
        frombody.add("address", newaddress);
        UtilHttp utilHttp = new UtilHttp();
        UtilHttp.ICallBack iCallBack = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onFailure: " + throwable);
                Toast.makeText(mcontext, "更新失败，请重试", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onSuccess(String response) {
                Log.i("TAG", "更新地址返回 " + response);
                dialog.dismiss();
            }
        };
        try {
            utilHttp.untilPostForm(frombody.build(), "address/updateaddress", iCallBack);
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView address_tv;
        ImageView update_address, delete_address;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            address_tv = itemView.findViewById(R.id.address_tv);
            update_address = itemView.findViewById(R.id.update_address);
            delete_address = itemView.findViewById(R.id.delete_address);

        }
    }

    /**
     * @param id 地址id
     *           删除地址，sqllite与后端数据库同时删除
     */
    private void deleteAddress(String id) {
        ProgressDialog dialog = new ProgressDialog(mcontext);
        dialog.setMessage("正在删除");
        dialog.show();

        //数据库删除数据
        AddressDBHelper dbHelper = new AddressDBHelper(mcontext, "address.db", null, 1);
        // 通过DBHelper类获取一个读写的SQLiteDatabase对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("address", "id=?", new String[]{id});
        // 释放连接
        db.close();

        //请求服务器删除后台地址数据
        FormBody.Builder frombody = new FormBody.Builder();
        frombody.add("addressid", id);
        UtilHttp utilHttp = new UtilHttp();
        UtilHttp.ICallBack iCallBack = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onFailure: " + throwable);
                Toast.makeText(mcontext, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onSuccess(String response) {
                Log.i("TAG", "删除地址返回 " + response);
                dialog.dismiss();
            }
        };
        try {
            utilHttp.untilPostForm(frombody.build(), "address/deleteAddress", iCallBack);
        } catch (Exception e) {
            Log.i(TAG, "deleteAddress: " + e.toString());
        }

    }
}