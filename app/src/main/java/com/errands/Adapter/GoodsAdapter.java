package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.errands.Model.Order;
import com.errands.Sophix.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 订单详情页面物品的adapter
 */
public class GoodsAdapter extends BaseAdapter {
    List<Order> orders = new ArrayList<>();
    Context mcontext;
    String type = null;

    public GoodsAdapter(List<Order> orders, String type, Context conText) {
        this.mcontext = conText;
        this.orders = orders;
        this.type = type;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.goods, viewGroup, false);
        final TextView add_description;
        final TextView add_goodsname;
        final TextView add_goodsamount;
        final TextView add_goodsestimation;
        final TextView add_goodsevidence;
        final ImageView add_taskpic;
        add_description = view.findViewById(R.id.add_description);
        add_goodsname = view.findViewById(R.id.add_goodsname);
        add_goodsamount = view.findViewById(R.id.add_goodsamount);
        add_goodsestimation = view.findViewById(R.id.add_goodsestimation);
        add_goodsevidence = view.findViewById(R.id.add_goodsevidence);
        add_taskpic = view.findViewById(R.id.add_taskpic);
        switch (type) {
            case "代购":
                add_goodsevidence.setVisibility(View.INVISIBLE);
                add_description.setText("描述：" + orders.get(i).getDescription());
                add_goodsname.setText("商品名：" + orders.get(i).getName());
                add_goodsamount.setText("商品数量：" + orders.get(i).getAmount());
                add_goodsestimation.setText("商品单价：" + orders.get(i).getEstimation());
                add_taskpic.setImageResource(R.drawable.erweima);
                break;
            case "代送":
                add_goodsname.setVisibility(View.INVISIBLE);
                add_goodsamount.setVisibility(View.INVISIBLE);
                add_goodsestimation.setVisibility(View.INVISIBLE);
                add_goodsevidence.setVisibility(View.INVISIBLE);
                add_taskpic.setImageResource(R.drawable.erweima);
                add_description.setText("商品描述：" + orders.get(i).getDescription());
                break;
            case "代取":
                add_goodsname.setVisibility(View.INVISIBLE);
                add_goodsamount.setVisibility(View.INVISIBLE);
                add_goodsestimation.setVisibility(View.INVISIBLE);
                add_description.setText("物品描述：" + orders.get(i).getDescription());
                add_taskpic.setImageResource(R.drawable.erweima);
                add_goodsevidence.setText("取货码：" + orders.get(i).getEvidence());
                break;
            default:
                break;
        }
        return view;
    }
}
