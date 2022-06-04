package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.errands.Model.Order;
import com.errands.Sophix.R;

import java.util.List;

public class Orderdetail_take_Adapter extends RecyclerView.Adapter<Orderdetail_take_Adapter.MyHolder> implements View.OnClickListener {
    private final List<Order> orderdetail_takes;

    @Override
    public void onClick(View view) {

    }

    public Orderdetail_take_Adapter(List<Order> orderdetail_takes, Context context) {
        this.orderdetail_takes = orderdetail_takes;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singe_task, parent, false);
        Orderdetail_take_Adapter.MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.add_goodsname.setVisibility(View.INVISIBLE);
        holder.add_goodsamount.setVisibility(View.INVISIBLE);
        holder.add_goodsestimation.setVisibility(View.INVISIBLE);
        holder.add_description.setText(orderdetail_takes.get(position).getDescription());
        holder.add_goodsevidence.setText(orderdetail_takes.get(position).getEvidence());
        holder.delete_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderdetail_takes.remove(position);//删除数据源,移除集合中当前下标的数据
                notifyItemRemoved(position);//刷新被删除的地方
                notifyItemRangeChanged(position, getItemCount()); //刷新被删除数据，以及其后面的数据
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderdetail_takes.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ImageView add_taskpic, delete_goods;
        private final TextView add_description;
        private final TextView add_goodsname;
        private final TextView add_goodsamount;
        private final TextView add_goodsestimation;
        private final TextView add_goodsevidence;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            add_taskpic = itemView.findViewById(R.id.add_taskpic);
            delete_goods = itemView.findViewById(R.id.delete_goods);
            add_description = itemView.findViewById(R.id.add_description);
            add_goodsname = itemView.findViewById(R.id.add_goodsname);
            add_goodsamount = itemView.findViewById(R.id.add_goodsamount);
            add_goodsestimation = itemView.findViewById(R.id.add_goodsestimation);
            add_goodsevidence = itemView.findViewById(R.id.add_goodsevidence);
        }
    }
}
