package com.errands.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.errands.Sophix.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class VH extends RecyclerView.ViewHolder {

     CircleImageView task_user_portrait;
     ImageView task_type;
     TextView task_state, destination, Task_location, task_price;
     Button task_btn_accept;

    public VH(@NonNull View itemView) {
        super(itemView);
        task_user_portrait = itemView.findViewById(R.id.task_user_portrait);
        task_type = itemView.findViewById(R.id.task_type);
        task_state = itemView.findViewById(R.id.task_state);
        destination = itemView.findViewById(R.id.destination);
        Task_location = itemView.findViewById(R.id.Task_location);
        task_price = itemView.findViewById(R.id.task_price);
        task_btn_accept = itemView.findViewById(R.id.task_btn_accept);

    }
}
