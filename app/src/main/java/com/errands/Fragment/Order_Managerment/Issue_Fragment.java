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

public class Issue_Fragment extends Fragment {
    List<OrderBase> issue_orders;
    Context context;
    //发布的，sign为1
    public Issue_Fragment(List<OrderBase> issue_orders, Context context) {
        this.issue_orders=issue_orders;
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
        View rootview = inflater.inflate(R.layout.fragment_issue_, container, false);
        RecyclerView frg_issue_rv = rootview.findViewById(R.id.frg_issue_rv);
        Order_management_adapter adapter=new Order_management_adapter(issue_orders,context,0);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        frg_issue_rv.setLayoutManager(manager);
        frg_issue_rv.setAdapter(adapter);
        return rootview;
    }
}