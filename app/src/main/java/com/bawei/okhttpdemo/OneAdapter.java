package com.bawei.okhttpdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gjl on 2016/11/22.
 */
public class OneAdapter extends BaseAdapter {
    ArrayList<String> list;
    Context mContext ;
    MyHelper myHelper;
    public OneAdapter(Context context,ArrayList<String> list){
        this.mContext=context;
        this.list= list;
        myHelper=new MyHelper(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setText(list.get(position));
        return textView;
    }

    //删除条目
    public void removeItem(String channel){
        list.remove(channel);
        notifyDataSetChanged();
        //从数据库中删除
        SQLiteDatabase database = myHelper.getReadableDatabase();
        database.delete("channel_one","name=?",new String[]{channel});
        database.close();
    }
    //添加条目
    public void addItem(String channel){
        list.add(channel);
        notifyDataSetChanged();
        //从数据库中增加
        SQLiteDatabase database = myHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",channel);
        database.insert("channel_one",null,values);
        database.close();
    }
}
