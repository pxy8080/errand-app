package com.errands.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.errands.Model.Msg;
import com.errands.Model.MyMessage;
import com.errands.Sophix.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*
MsgAdapter消息界面的Adapter
 */

public class MsgAdapter extends ArrayAdapter<MyMessage> {

    public MsgAdapter(Context context, int textViewResourceId, List<MyMessage> objects) {
        super(context, textViewResourceId, objects);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyMessage msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.msg_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        if (msg.getType() == Msg.TYPE_RECEIVED) {
            // 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent());
            viewHolder.left_time.setText(msg.getTime());
        } else if (msg.getType() == Msg.TYPE_SENT) {
            // 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getContent());
            viewHolder.right_time.setText(msg.getTime());
        }
        return view;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView leftMsg; //左边消息
        LinearLayout leftLayout; //左边布局
        TextView rightMsg; //右边消息
        LinearLayout rightLayout; //右边布局
        TextView left_time;  //左边的时间
        TextView right_time; //右边时间
        CircleImageView l_oppositeuser_icon; //左边用户头像
        CircleImageView r_oppositeuser_icon; //右边用户头像

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftMsg = itemView.findViewById(R.id.left_msg);
            leftLayout = itemView.findViewById(R.id.left_layout);
            rightMsg = itemView.findViewById(R.id.right_msg);
            rightLayout = itemView.findViewById(R.id.right_layout);

            left_time = itemView.findViewById(R.id.left_time);
            right_time = itemView.findViewById(R.id.right_time);
            l_oppositeuser_icon = itemView.findViewById(R.id.l_oppositeuser_icon);
            r_oppositeuser_icon = itemView.findViewById(R.id.r_oppositeuser_icon);
        }
    }
}