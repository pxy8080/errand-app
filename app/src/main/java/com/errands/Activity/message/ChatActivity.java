package com.errands.Activity.message;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.errands.Activity.BaseActivity;
import com.errands.Chat.PublicData;
import com.errands.Chat.SocketThread;
import com.errands.Sophix.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatActivity extends BaseActivity implements View.OnClickListener{
    public TextView infolist;
    public TextInputEditText sendinfo;
    public Button btn_send;
    public Handler handler;
    public TextInputEditText fromText, toText;
    private ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        InitView();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case PublicData.Send_MSG_Code:
                        infolist.append("\n" + msg.obj.toString());
                        break;
                }
            }
        };

//        SocketThread socketThread=new SocketThread(handler,"1",this,);
//        socketThread.start();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String messageSend = sendinfo.getText().toString().trim();
                    String fromid = fromText.getText().toString().trim();
                    String toid = toText.getText().toString().trim();
                    JSONObject json = new JSONObject();
                    json.put("to", toid);
                    json.put("msg", messageSend);

                    //把ui线程的消息发送给非ui线程
                    Message msg = new Message();
                    msg.what = PublicData.Send_MSG_Code;
                    msg.obj = json.toString();
                    SocketThread.reHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(PublicData.ERROR_INFO, e.getMessage());
                }
            }
        });
    }

    public void InitView() {
        infolist=findViewById(R.id.textView);
        sendinfo=findViewById(R.id.textInputLayout);
        btn_send=findViewById(R.id.send);
        fromText=findViewById(R.id.fromid);
        toText=findViewById(R.id.toid);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.back_img:
//                onBackPressed();
//                break;
            default:break;
        }

    }

    private String getTime(long millTime) {
        Date d = new Date(millTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(d));
        return sdf.format(d);
    }
}