package com.errands.Fragment.Order_Managerment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.errands.Adapter.Order_management_adapter;
import com.errands.Model.OrderBase;
import com.errands.Sophix.R;

import java.util.List;


public class Receive_Fragment extends Fragment {


    private List<OrderBase> receive_orders;
    private Context context;
    public Receive_Fragment(List<OrderBase> receive_orders, Context context) {
        this.receive_orders=receive_orders;
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //拿到根view
        View rootview=inflater.inflate(R.layout.fragment_receive_, container, false);
        RecyclerView frg_receive_rv=rootview.findViewById(R.id.frg_receive_rv);
        System.out.println("拿到的receive  "+receive_orders.toString());

        Order_management_adapter adapter=new Order_management_adapter(receive_orders,context,0);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        frg_receive_rv.setLayoutManager(manager);
        frg_receive_rv.setAdapter(adapter);
        return rootview;
    }
}