package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.errands.Activity.MainActivity;
import com.errands.Https.UtilHttp;
import com.errands.Model.Bill;
import com.errands.Model.OrderBase;
import com.errands.Sophix.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order_management_adapter extends RecyclerView.Adapter<Order_management_adapter.MyHolder> {
    private Context context;
    private List<OrderBase> orderBases = new ArrayList<>();
    private int sign;

    /**
     * @param orderBases 传过来的orderbase
     * @param context    上下文
     * @param sign       标记是发布的订单还是接受的订单 0发布 1接受
     */
    public Order_management_adapter(List<OrderBase> orderBases, Context context, int sign) {
        this.context = context;
        this.orderBases = orderBases;
        this.sign = sign;
    }


    @NonNull
    @Override
    public Order_management_adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_simple, parent, false);
        Order_management_adapter.MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull Order_management_adapter.MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.order_id.setText("订单编号：" + orderBases.get(position).getId());
        holder.order_type.setText("订单类型：" + orderBases.get(position).getType());
        //根据订单
        if (sign == 1) {
            holder.person.setText("发单者：" + orderBases.get(position).getUser_id_receive());
            //判断订单转状态，给textview不同的颜色
            if (orderBases.get(position).getState() == 1) {
                holder.order_state.setText("待接单");
                holder.order_state.setTextColor(Color.BLUE);
            } else if (orderBases.get(position).getState() == 2) {
                holder.order_state.setText("进行中");
                holder.order_state.setTextColor(Color.RED);
            } else if (orderBases.get(position).getState() == 3) {
                holder.order_state.setText("订单已完成");
                holder.order_state.setTextColor(Color.BLACK);
            }
        } else if (sign == 0) {
            holder.person.setText("接单者：" + orderBases.get(position).getUser_id_receive());
            if (orderBases.get(position).getState() == 1) {
                holder.order_state.setText("待接单");
                holder.order_state.setTextColor(Color.BLUE);
            } else if (orderBases.get(position).getState() == 2) {
                holder.order_state.setText("进行中");
                holder.order_state.setTextColor(Color.RED);
            } else if (orderBases.get(position).getState() == 3) {
                holder.order_state.setText("订单已完成");
                holder.order_state.setTextColor(Color.BLACK);
            }
        }
        //选择自己进行发布中的订单，可以更改问订单完成
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"点击了"+position,Toast.LENGTH_SHORT).show();
                if (sign == 0) {
                    //如果是发布的订单且在进行中，通知用户提交接口可以完成订单
                    if (orderBases.get(position).getState() == 2) {
                        AlertDialog dialog = new AlertDialog.Builder(context)
                                .setIcon(R.mipmap.logo)//设置标题的图片
                                .setTitle("通知")//设置对话框的标题
                                .setMessage("确定订单完成？")//设置对话框的内容
                                //设置对话框的按钮
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finishorder(orderBases.get(position));
                                        dialog.dismiss();
                                    }
                                }).create();
                        dialog.show();
                    }
                }
            }
        });

        holder.order_time.setText("时间：" + orderBases.get(position).getDate() + " " + orderBases.get(position).getTime());
        holder.order_price.setText("价格：" + orderBases.get(position).getPrice());
    }


    //修改基础订单状态为3---完成,同时添加账单
    void finishorder(OrderBase orderBase){
        orderBase.setState(3);
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack iCallBack = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onSuccess: update失败" + throwable);
            }

            @Override
            public void onSuccess(String response) {
                Log.i("TAG", "onSuccess: update成功" + response);
            }
        };
        try {
            utilHttp.untilPostJson("order/updateOrder"  , JSON.toJSON(orderBase).toString(), iCallBack);
        } catch (Exception e) {

        }


        String[] DateTime=getTime(System.currentTimeMillis()).split(",");
        Bill bill=new Bill(null,orderBase.getUser_id_send(),orderBase.getId(),DateTime[0],DateTime[1],0,Double.parseDouble(orderBase.getPrice()));
        UtilHttp utilHttp2 = UtilHttp.obtain();
        UtilHttp.ICallBack iCallBack2 = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onSuccess: addBill失败" + throwable);
            }

            @Override
            public void onSuccess(String response) {
                Log.i("TAG", "onSuccess: addBill成功" + response);
            }
        };
        try {
            utilHttp2.untilPostJson("order/addBill"  , JSON.toJSON(bill).toString(), iCallBack2);
        } catch (Exception e) {
        }

        Bill bill2=new Bill(null,orderBase.getUser_id_receive(),orderBase.getId(),DateTime[0],DateTime[1],Double.parseDouble(orderBase.getPrice()),0);
        UtilHttp utilHttp3 = UtilHttp.obtain();
        UtilHttp.ICallBack iCallBack3 = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onSuccess: addBill失败" + throwable);
            }

            @Override
            public void onSuccess(String response) {
                Log.i("TAG", "onSuccess: addBill成功" + response);
            }
        };
        try {
            utilHttp3.untilPostJson("order/addBill"  , JSON.toJSON(bill2).toString(), iCallBack3);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return orderBases.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView order_id, order_type, person, order_state, order_time, order_price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.order_id);
            order_type = itemView.findViewById(R.id.order_type);
            person = itemView.findViewById(R.id.person);
            order_state = itemView.findViewById(R.id.order_state);
            order_time = itemView.findViewById(R.id.order_time);
            order_price = itemView.findViewById(R.id.order_price);
        }
    }

    private String getTime(long millTime) {
        Date d = new Date(millTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        System.out.println(sdf.format(d));
        return sdf.format(d);
    }
}
