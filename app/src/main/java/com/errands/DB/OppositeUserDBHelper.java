package com.errands.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OppositeUserDBHelper extends SQLiteOpenHelper {
    public OppositeUserDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库sql语句
        String sql = "create table oppositeuser(oppositeuser_id varchar(32),oppositeuser_phone varchar(13),oppositeuser_icon varchar(128))";
        //执行sql语句
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
