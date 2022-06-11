package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.errands.Model.OrderBase;
import com.errands.Sophix.R;

import java.util.ArrayList;
import java.util.List;

public class Order_management_adapter extends RecyclerView.Adapter<Order_management_adapter.MyHolder> {
    private Context context;
    private List<OrderBase> orderBases = new ArrayList<>();
    private int sign;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Order_management_adapter.MyHolder holder, int position) {
        System.out.println("zzzzzzzzzzzzz"+orderBases.size());
        holder.order_id.setText("订单编号：" + orderBases.get(position).getId());
        holder.order_type.setText("订单类型：" + orderBases.get(position).getType());
        if (sign == 0) {
            holder.person.setText("发单者：" + orderBases.get(position).getUser_id_receive());
        } else if (sign == 1) {
            holder.person.setText("接单者：" + orderBases.get(position).getUser_id_receive());
        }
        holder.order_state.setText("状态：" + orderBases.get(position).getState());
        holder.order_time.setText("时间：" + orderBases.get(position).getDate() + " " + orderBases.get(position).getTime());
        holder.order_price.setText("价格：" + orderBases.get(position).getPrice());
        System.out.println("数据测试：" + orderBases.get(position).getPrice());
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
}
