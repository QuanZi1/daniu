package com.bawei.okhttpdemo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChannelActivity extends Activity implements AdapterView.OnItemClickListener {

    private GridView one;
    private GridView two;
    private OneAdapter oneAdapter;
    private TwoAdapter twoAdapter;
    private  final String TAG = "ChannelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        //初始化界面
        one = (GridView) findViewById(R.id.one);
        two = (GridView) findViewById(R.id.two);
        //查询数据库
        ArrayList<String> channel_one = getDataFromDataBase("channel_one");
        ArrayList<String> channel_two = getDataFromDataBase("channel_two");
        //显示数据tableName
        oneAdapter = new OneAdapter(this, channel_one);
        twoAdapter = new TwoAdapter(this, channel_two);
        one.setAdapter(oneAdapter);
        two.setAdapter(twoAdapter);

        one.setOnItemClickListener(this);
        two.setOnItemClickListener(this);
    }

    public void back(View view){
        finish();
    }

    private ArrayList<String> getDataFromDataBase(String tableName) {
        ArrayList<String> cs = new ArrayList<>();
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase readableDatabase = myHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from "+tableName, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            cs.add(name);
        }
        return cs;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.one:
                Log.d(TAG, "onItemClick() returned: " + "one"+position);
                TextView textView = (TextView) view;
                String item = textView.getText().toString();
                oneAdapter.removeItem(item);
                twoAdapter.addItem(item);
                break;
            case R.id.two:
                Log.d(TAG, "onItemClick() returned: " + "one"+position);
                TextView textView1 = (TextView) view;
                String item1 = textView1.getText().toString();
                oneAdapter.addItem(item1);
                twoAdapter.removeItem(item1);
                break;
        }
    }
}
