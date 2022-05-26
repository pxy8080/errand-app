package com.errands.Activity.message;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.errands.Adapter.MsgAdapter;
import com.errands.Chat.PublicData;
import com.errands.Chat.SocketThread;
import com.errands.Model.Msg;
import com.errands.Sophix.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity2 extends AppCompatActivity implements View.OnClickListener {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();
    private ImageView back_img;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        initView();

        msgListView = findViewById(R.id.msg_list_view);
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);

        initMsgs();
        adapter = new MsgAdapter(ChatActivity2.this, R.layout.msg_item, msgList);
        msgListView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    // 当有新消息时，刷新ListView中的显示
                    adapter.notifyDataSetChanged();
                    // 将ListView定位到最后一行
                    msgListView.setSelection(msgList.size());
                    // 清空输入框中的内容
                    inputText.setText(null);
                    JSONObject json = new JSONObject();

                    try {
                        json.put("msg", content);
                        json.put("to", "80c2524fc84c11ec9bf500163e0ce512");
                        //把ui线程的消息发送给非ui线程
                        Message msg2 = new Message();
                        msg2.what = PublicData.Send_MSG_Code;
                        msg2.obj = json.toString();
                        SocketThread.reHandler.sendMessage(msg2);
                    } catch (JSONException e) {
                        e.printStackTrace();
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
    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
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
}