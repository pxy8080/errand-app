package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.errands.Activity.HomeActivity;
import com.errands.Activity.LoginActivity;
import com.errands.Https.UtilHttp;
import com.errands.Model.OrderBase;
import com.errands.Model.Result;
import com.errands.Model.User;
import com.errands.Sophix.R;
import com.errands.Util.GlideUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TaskAdapter首页任务的Adapter
 */
public class TaskAdapter extends RecyclerView.Adapter<VH> {
    private List<OrderBase> orderBase = new ArrayList<>();
    private final Context context;
    private Handler handler;
    String path, nickname;

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

    @SuppressLint({"SetTextI18n", "HandlerLeak"})
    @Override
    public void onBindViewHolder(@NonNull VH holder, @SuppressLint("RecyclerView") int position) {
        holder.task_state.setText("" + orderBase.get(position).getName());
        holder.destination.setText("To:" + orderBase.get(position).getTaskAddress());
        holder.task_price.setText(String.valueOf(orderBase.get(position).getPrice()));
        holder.task_time.setText(orderBase.get(position).getDate() + "\n" + orderBase.get(position).getTime());
        getnicknameandhead(orderBase.get(position).getUser_id_send());
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    String res[] = String.valueOf(msg.obj).split(",");
                    path = res[0].substring(1);
                    nickname = res[1].substring(0, res[1].length() - 1);
                    holder.employer.setText("发单人"+nickname);
                    GlideUtil.loadImageView(context, path, holder.task_user_portrait);
                }
            }
        };


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
        holder.task_btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClikListener != null) {
                    onItemClikListener.onItem(position);
                }
            }
        });
    }

    void getnicknameandhead(String id) {
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onFailure: " + throwable);
            }

            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                Result result = gson.fromJson(response, new TypeToken<Result>() {
                }.getType());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result.getData();
                handler.sendMessage(msg);
            }
        };
        try {
            utilHttp.utilGet("user/getUserHeadAndNameById/?id=" + id, callback);
        } catch (Exception e) {

        }
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
