package com.example.remoteview;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //当设置标志位失败的时候，可以通过manager的cancel方法，取消对应的通知
        int id = getIntent().getIntExtra("id", -1);
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(id);
    }
}
