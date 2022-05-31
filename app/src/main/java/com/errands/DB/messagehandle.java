package com.errands.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSONObject;
import com.errands.Model.Address;
import com.errands.Model.MyMessage;

import java.util.ArrayList;
import java.util.List;

public class messagehandle {


    //保存收到的信息
    public static void savemessage(String serverinfo, Context context) {
        JSONObject json = JSONObject.parseObject(serverinfo);
        String from = json.getString("from");
        String msg = json.getString("msg");
        String time = json.getString("time");
        //依靠DatabaseHelper带全部参数的构造函数创建数据库
        MessageDBHelper dbHelper = new MessageDBHelper(context, "message.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put("to_id", from);
            values.put("msg_type", 0);
            values.put("Message", msg);
            values.put("time", time);
            db.insert("message", null, values);
            System.out.println("插入消息成功" + from + "--" + msg + "---" + time);
        } catch (Exception e) {
            System.out.println("插入错误" + e.toString());
        }
    }

    public static void savemessbymsg(MyMessage message, Context context) {

        //依靠DatabaseHelper带全部参数的构造函数创建数据库
        MessageDBHelper dbHelper = new MessageDBHelper(context, "message.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put("to_id", message.getOppositeuser_id());
            values.put("msg_type", message.getType());
            values.put("Message", message.getContent());
            values.put("time", message.getTime());
            db.insert("message", null, values);
            System.out.println("插入消息成功");
        } catch (Exception e) {
            System.out.println("插入错误" + e.toString());
        }
    }

    public static List<MyMessage> getmessage(String oppositeuser_id, Context context) {
        List<MyMessage> myMessages = new ArrayList<>();
        MessageDBHelper dbHelper = new MessageDBHelper(context, "message.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("message", new String[]{"to_id", "msg_type", "Message", "time"}, null, null, null, null, null);
        System.out.println("值" + cursor.moveToNext());
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String to_id = cursor.getString(cursor.getColumnIndex("to_id"));
            @SuppressLint("Range") int msg_type = cursor.getInt(cursor.getColumnIndex("msg_type"));
            @SuppressLint("Range") String Message = cursor.getString(cursor.getColumnIndex("Message"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            myMessages.add(new MyMessage(to_id, msg_type, Message, time));
        }
        db.close();
        return myMessages;
    }
}
