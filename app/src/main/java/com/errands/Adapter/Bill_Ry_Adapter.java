package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.errands.Model.Billinfo;
import com.errands.Sophix.R;

import java.util.List;

/*
账单页面recyclerview适配器
 */
public class Bill_Ry_Adapter extends RecyclerView.Adapter<Bill_Ry_Adapter.MyHolder> {
    private List<Billinfo> bills;

    public Bill_Ry_Adapter(List<Billinfo> bills) {
        this.bills = bills;
    }

    @NonNull
    @Override
    public Bill_Ry_Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_record, parent, false);
        Bill_Ry_Adapter.MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull Bill_Ry_Adapter.MyHolder holder, int position) {
        holder.bill_id.setText("账单id：" + bills.get(position).getId());
        holder.bill_time.setText("账单时间：" + bills.get(position).getDate() + " " + bills.get(position).getTime());
        holder.bill_employee.setText("发单人：" + bills.get(position).getUserName());
        holder.bill_type.setText("账单类型：" + bills.get(position).getType());
        if (bills.get(position).getPayment() == 0) {
            holder.bill_num.setTextColor(Color.GREEN);
            holder.bill_num.setText("+" + bills.get(position).getIncome());
        } else if (bills.get(position).getIncome() == 0) {
            holder.bill_num.setTextColor(Color.RED);
            holder.bill_num.setText("-" + bills.get(position).getPayment());
        }
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView bill_id, bill_employee, bill_type, bill_time, bill_num;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            bill_id = itemView.findViewById(R.id.bill_id);
            bill_employee = itemView.findViewById(R.id.bill_employee);
            bill_type = itemView.findViewById(R.id.bill_type);
            bill_time = itemView.findViewById(R.id.bill_time);
            bill_num = itemView.findViewById(R.id.bill_num);
        }
    }
}
