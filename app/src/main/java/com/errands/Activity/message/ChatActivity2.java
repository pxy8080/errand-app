package com.errands.Activity.message;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.errands.Activity.BaseActivity;
import com.errands.Adapter.MsgAdapter;
import com.errands.Chat.PublicData;
import com.errands.Chat.SocketThread;
import com.errands.DB.messagehandle;
import com.errands.Model.Msg;
import com.errands.Model.MyMessage;
import com.errands.Sophix.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity2 extends BaseActivity implements View.OnClickListener {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<MyMessage> msgList = new ArrayList<MyMessage>();
    private ImageView back_img;
    private TextView title;
    private EditText to;
    public Handler handler;
    private String oppositeuser_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        initView();

        initMsgs();
        adapter = new MsgAdapter(ChatActivity2.this, R.layout.msg_item, msgList);
        msgListView.setAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case PublicData.Send_MSG_Code:
                        break;
                }
            }
        };

        SocketThread socketThread = new SocketThread(handler, account.getId(), this);
        socketThread.start();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                String toid = to.getText().toString().trim();
                if (!"".equals(content)) {
                    MyMessage msg = new MyMessage(toid, Msg.TYPE_SENT, content, getTime(System.currentTimeMillis()));
                    messagehandle.savemessbymsg(msg, ChatActivity2.this);
                    msgList.add(msg);
                    // 当有新消息时，刷新ListView中的显示
                    adapter.notifyDataSetChanged();
                    // 将ListView定位到最后一行
                    msgListView.setSelection(msgList.size());
                    // 清空输入框中的内容
                    inputText.setText(null);
                    try {
                        JSONObject json = new JSONObject();
                        json.put("msg", content);
                        json.put("to", toid);
                        //把ui线程的消息发送给非ui线程
                        Message msg2 = new Message();
                        msg2.what = PublicData.Send_MSG_Code;
                        msg2.obj = json.toString();
                        SocketThread.reHandler.sendMessage(msg2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(PublicData.ERROR_INFO, e.getMessage());
                    }
                }
            }
        });
    }

    private void initView() {
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("用户id");
        msgListView = findViewById(R.id.msg_list_view);
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        to = findViewById(R.id.to);
    }

    private void initMsgs() {
        oppositeuser_id = "4c191912c23811ec9bf500163e0ce512";
        msgList = messagehandle.getmessage(oppositeuser_id, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private String getTime(long millTime) {
        Date d = new Date(millTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(d));
        return sdf.format(d);
    }
}