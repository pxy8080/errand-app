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

public class Orderdetail_buy_Adapter extends RecyclerView.Adapter<Orderdetail_buy_Adapter.MyHolder> implements View.OnClickListener {
    private List<Order> orderdetail_buys;
    private Context context;
    private buy_onclick mOnItemClickListener;

    @Override
    public void onClick(View view) {

    }

    public Orderdetail_buy_Adapter(List<Order> orderdetail_buys, Context context) {
        this.context = context;
        this.orderdetail_buys = orderdetail_buys;
    }

    public Orderdetail_buy_Adapter() {

    }

    @NonNull
    @Override
    public Orderdetail_buy_Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singe_task, parent, false);
        Orderdetail_buy_Adapter.MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Orderdetail_buy_Adapter.MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.add_goodsevidence.setVisibility(View.INVISIBLE);
        holder.delete_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderdetail_buys.remove(position);//删除数据源,移除集合中当前下标的数据
                notifyItemRemoved(position);//刷新被删除的地方
                notifyItemRangeChanged(position, getItemCount()); //刷新被删除数据，以及其后面的数据
            }
        });
        holder.add_goodsestimation.setText(String.valueOf(orderdetail_buys.get(position).getEstimation()));
        holder.add_goodsamount.setText(String.valueOf(orderdetail_buys.get(position).getAmount()));
        holder.add_goodsname.setText(orderdetail_buys.get(position).getName());
        holder.add_description.setText(orderdetail_buys.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return orderdetail_buys.size();
    }

    /**
     * 编写回调的步骤
     * 1.创建这个接口
     * 2.定义接口内部方法
     * 3.提供设置接口方法给外部
     * 4.接口的调用
     */
    public interface buy_onclick {
        void onItemClick(int position);
    }

    //设置接口的方法
    public void buy_onclick(buy_onclick listener) {
        this.mOnItemClickListener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private final ImageView delete_goods;
        private final TextView add_description;
        private final TextView add_goodsname;
        private final TextView add_goodsamount;
        private final TextView add_goodsestimation;
        private final TextView add_goodsevidence;
        private int mPosition;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ImageView add_taskpic = itemView.findViewById(R.id.add_taskpic);
            delete_goods = itemView.findViewById(R.id.delete_goods);
            add_description = itemView.findViewById(R.id.add_description);
            add_goodsname = itemView.findViewById(R.id.add_goodsname);
            add_goodsamount = itemView.findViewById(R.id.add_goodsamount);
            add_goodsestimation = itemView.findViewById(R.id.add_goodsestimation);
            add_goodsevidence = itemView.findViewById(R.id.add_goodsevidence);

            add_taskpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        //当传入的mOnItemClickListener不为空就执行其中的方法
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });
        }

        public void setmPosition(int mPosition) {
            this.mPosition = mPosition;
        }
    }


}
