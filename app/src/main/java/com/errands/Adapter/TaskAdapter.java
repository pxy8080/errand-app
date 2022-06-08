package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.errands.Model.OrderBase;
import com.errands.Sophix.R;
import com.errands.Util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskAdapter首页任务的Adapter
 */
public class TaskAdapter extends RecyclerView.Adapter<VH> {
    private List<OrderBase> orderBase = new ArrayList<>();
    private final Context context;

    public TaskAdapter(List<OrderBase> orderBase, Context context) {
        this.context = context;
        this.orderBase = orderBase;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VH holder, @SuppressLint("RecyclerView") int position) {
        holder.task_state.setText("" + orderBase.get(position).getName());
        holder.destination.setText("To:" + orderBase.get(position).getTaskAddress());
        holder.employer.setText(orderBase.get(position).getUser_id_send());
        holder.task_price.setText(String.valueOf(orderBase.get(position).getTaskAddress()));
        holder.task_time.setText(orderBase.get(position).getDate() + "\n" + orderBase.get(position).getTime());
        String testpath = "https://img0.baidu.com/it/u=3798217922,3880088897&fm=253&fmt=auto&app=120&f=JPEG?w=889&h=500";
        GlideUtil.loadImageViewLodingSize(context, testpath, 60, 60, holder.task_user_portrait, R.drawable.goods, R.drawable.goods);
        switch (orderBase.get(position).getType()) {
            case "代购":
                holder.task_type.setImageResource(R.drawable.icon1);
                break;
            case "代取":
                holder.task_type.setImageResource(R.drawable.icon2);
                break;
            case "代送":
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
