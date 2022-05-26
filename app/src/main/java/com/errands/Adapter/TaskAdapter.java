package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.errands.Sophix.R;

public class TaskAdapter extends RecyclerView.Adapter<VH> {

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        VH holder = new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, @SuppressLint("RecyclerView") int position) {
        holder.task_state.setText("这是一个简介");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClikListener!=null){
                    onItemClikListener.onItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public interface OnItemClikListener{
        void onItem(int position);
    }
    OnItemClikListener onItemClikListener;

    public void setOnItemClikListener(OnItemClikListener onItemClikListener) {
        this.onItemClikListener = onItemClikListener;
    }
}