package com.bawei.okhttpdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * OKHttp
 * 依赖OKIO
 */
public class MainActivity extends FragmentActivity {
    private String[] channels = new String[]{

            "推荐", "头条", "社会", "国际", "国内", "体育", "娱乐", "军事"
    };
    private ArrayList<String> list;
    private LinearLayout channel;
    private ViewPager viewPager;
    private ArrayList<NewFragment> fs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化数据库
        initDataBase();

        channel = (LinearLayout) findViewById(R.id.channel);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //显示选中的频道

        //查询数据库

        list = getDataFromDataBase();
        //刷新数据
        refreshData(list);
        //viewpager设置适配器
    }

    private void refreshData(ArrayList<String> list) {
        if (channel.getChildCount() > 0) {
            channel.removeAllViews();
        }
        if (fs!=null){
            fs.clear();
        }
        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(list.get(i));
            channel.addView(textView);
        }

        for (int i=0;i<list.size();i++){
            NewFragment instance = NewFragment.getInstance(list.get(i));
            fs.add(instance);
        }
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fs);
        viewPager.setAdapter(adapter);
    }

    private ArrayList<String> getDataFromDataBase() {
        ArrayList<String> cs = new ArrayList<>();
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase readableDatabase = myHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from channel_one", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            cs.add(name);
        }
        return cs;
    }

    //跳转
    public void more(View view) {
        startActivityForResult(new Intent(MainActivity.this, ChannelActivity.class), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<String> dataFromDataBase = getDataFromDataBase();
        refreshData(dataFromDataBase);
    }

    private void initDataBase() {
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase readableDatabase = myHelper.getReadableDatabase();
        for (int i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            values.put("name", channels[i]);
            readableDatabase.insert("channel_one", null, values);
        }
        for (int i = 6; i < channels.length; i++) {
            ContentValues values = new ContentValues();
            values.put("name", channels[i]);
            readableDatabase.insert("channel_two", null, values);
        }
        readableDatabase.close();
    }
    //适配器
    public class MyFragmentAdapter extends FragmentPagerAdapter{

        private ArrayList<NewFragment> list;
        public MyFragmentAdapter(FragmentManager fm,ArrayList<NewFragment> list) {
            super(fm);
            this.list=list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
