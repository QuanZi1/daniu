package com.bawei.okhttpdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gjl on 2016/11/22.
 */
public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(Context context) {
        super(context, "channel.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table channel_one(_id integer primary key autoincrement,name text)");
        db.execSQL("create table channel_two(_id integer primary key autoincrement,name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
