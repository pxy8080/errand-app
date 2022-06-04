package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.errands.Model.DetailOrder;
import com.errands.Model.OrderBase;
import com.errands.Sophix.R;
import com.errands.Util.GlideUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<VH> {
    private List<OrderBase> orderBase = new ArrayList<>();
    private Context context;
    private String testpath = "http://81.71.163.138:8080/examples/test/99dacd765ba14d11b4b0a4d153a9ce3d-1652357283272.jpg";

    public TaskAdapter(List<OrderBase> orderBase, Context context) {
        this.context = context;
        this.orderBase = orderBase;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        VH holder = new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, @SuppressLint("RecyclerView") int position) {
        holder.task_state.setText("" + orderBase.get(position).getState());
        holder.destination.setText("To:" + orderBase.get(position).getTaskAddress());
        holder.employer.setText(orderBase.get(position).getName());
        holder.task_price.setText(String.valueOf(orderBase.get(position).getTaskAddress()));
        holder.task_time.setText(orderBase.get(position).getDate() + " " + orderBase.get(position).getTime());
        GlideUtil.loadImageViewLodingSize(context, testpath, 60, 60, holder.task_user_portrait, R.drawable.goods, R.drawable.goods);
        switch (orderBase.get(position).getType()) {
            case "1":
                holder.task_type.setImageResource(R.drawable.icon1);
                break;
            case "2":
                holder.task_type.setImageResource(R.drawable.icon2);
                break;
            case "3":
                holder.task_type.setImageResource(R.drawable.icon3);
                break;
            default:
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClikListener != null) {
                    onItemClikListener.onItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderBase.size();
    }

    public interface OnItemClikListener {
        void onItem(int position);
    }

    OnItemClikListener onItemClikListener;

    public void setOnItemClikListener(OnItemClikListener onItemClikListener) {
        this.onItemClikListener = onItemClikListener;
    }
}
