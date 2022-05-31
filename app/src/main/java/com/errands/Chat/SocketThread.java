package com.errands.Chat;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.errands.Adapter.MsgAdapter;
import com.errands.DB.messagehandle;
import com.errands.Model.MyMessage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SocketThread extends Thread {
    public Socket socket;
    public static Handler reHandler;
    public OutputStream outputStream;
    public Handler mainHandler;
    public String mUserID;
    public Context mcontext;

    public SocketThread(Handler handler, String userID, Context context) {
        mcontext = context;
        mainHandler = handler;
        mUserID = userID;

    }

    @Override
    public void run() {
//        1.创建socket
        try {
            socket = new Socket("10.0.2.2", 20000);//创建socket客户端发起连接
//            //2.写入数据
            outputStream = socket.getOutputStream();
            JSONObject json = new JSONObject();
            json.put("msg", mUserID);
            json.put("to", "userIDmessage");
            outputStream.write((json + "\n").getBytes(StandardCharsets.UTF_8));
            new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                        String serverinfo = null;
                        while ((serverinfo = br.readLine()) != null) {
                            Log.i("来自服务器的消息", serverinfo);
                            com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(serverinfo);
                            String from = json.getString("from");
                            String message = json.getString("msg");
                            String time = json.getString("time");
                            MyMessage m=new MyMessage(from,1,message,time);



//                            把服务器的消息发送ui线程进行显示
                            Message msg = new Message();
                            msg.what = PublicData.Rev_MSG_Code;
                            msg.obj = serverinfo;
                            mainHandler.sendMessage(msg);
                            messagehandle.savemessbymsg(m, mcontext);
                        }
//

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(PublicData.ERROR_INFO, e.getMessage());
                    }
                }
            }.start();


            //3.关闭连接
//            socket.close();
            //非UI线程与UI线程消息的循环
            //为当前线程 进行实例化
            Looper.prepare();//循环准备
            reHandler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    switch (msg.what) {
                        case PublicData.Send_MSG_Code:
                            try {
                                outputStream = socket.getOutputStream();
                                outputStream.write((msg.obj.toString() + "\n").getBytes(StandardCharsets.UTF_8));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            break;

                    }
                }
            };
            Looper.loop();//启动循环
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("来自报错", e.getMessage());
        }
    }
}
