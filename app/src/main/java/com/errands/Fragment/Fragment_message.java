package com.errands.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.errands.Activity.message.ChatActivity;
import com.errands.Activity.message.ChatActivity2;
import com.errands.Chat.PublicData;
import com.errands.Chat.SocketThread;
import com.errands.Sophix.R;

import org.json.JSONObject;

public class Fragment_message extends Fragment {
    private View rootView;
    private Toolbar title_bar;
    private TextView title;
    private ImageView back;
    private RecyclerView list_chat_recyclerview;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Fragment_message() {
    }


    public static Fragment_message newInstance(String param1, String param2) {
        Fragment_message fragment = new Fragment_message();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_message, container, false);
        back = rootView.findViewById(R.id.back_img);
        back.setVisibility(View.INVISIBLE);
        title = rootView.findViewById(R.id.title);
        title.setText("消息");

        list_chat_recyclerview = rootView.findViewById(R.id.list_chat_recyclerview);

        RelativeLayout relativeLayout;
        relativeLayout = rootView.findViewById(R.id.chat_detail);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity2.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void initThread() {
        Handler handler;
        //handler=new SocketHandler();
        //创建连接socket服务器的线程并启动
        String userid = "1";
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case PublicData.Send_MSG_Code:
                        break;
                }
            }
        };
        SocketThread socketThread = new SocketThread(handler, userid);
        socketThread.start();

    }
}