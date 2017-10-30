package com.example.remoteview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void notify_normal(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //通过builder创建一个通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder
                .setTicker("您有一条新消息")//第一次通知时，显示在actionbar上面的内容
                .setContentTitle("from QQ") //内容设置标题
                .setContentText("What are you 弄啥嘞") //设置内容,如果内容中有!，在魅族手机中消息是弹不出来的
                .setSmallIcon(R.mipmap.ic_launcher_round) //设置显示的图标
                .setWhen(System.currentTimeMillis()) //设置消息什么时候发送
                .setAutoCancel(true)//用户点击后是否自动取消
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//设置消息的优先级
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                //建议魅族手机开启这个属性，否则如果信息中包括！号，无论是英文模式还是中文模式，都无法显示通知1容易发送不出去消息
                .setOngoing(false) //﻿ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setContentIntent(pendingIntent)//用户点击消息执行的意图动作
                .build();
        ;

        //通过NotificationManager发送消息
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1, notification);


    }

    public void notify_remote(View view) {
        int notificationId = 0;
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("id", notificationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.layout_notifiycation_view);
        views.setImageViewResource(R.id.iv_icon, R.mipmap.myheader);
        views.setTextViewText(R.id.tv_title, "from QQ");
        views.setTextViewText(R.id.tv_content, "呵呵呵呵，哈哈哈哈，原来是那啥惹的祸");
        views.setOnClickPendingIntent(R.id.custom_notification, pendingIntent);
        //通过builder创建一个通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder
                .setTicker("自定义消息")//第一次通知时，显示在actionbar上面的内容
                .setSmallIcon(R.mipmap.ic_launcher_round) //设置显示的图标
                .setWhen(System.currentTimeMillis()) //设置消息什么时候发送
                .setContent(views)
                .setAutoCancel(true)//用户点击后是否自动取消
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//设置消息的优先级
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                //建议魅族手机开启这个属性，否则如果信息中包括！号，无论是英文模式还是中文模式，都无法显示通知1容易发送不出去消息
                .setOngoing(false) //﻿ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//                .setContentIntent(pendingIntent)//用户点击消息执行的意图动作
                .build();
        ;

        //通过NotificationManager发送消息
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(notificationId, notification);
    }

    int num = 0;

    public void notifyProgress(View view) {
        //通过builder创建一个通知
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        final Notification notification = builder
                .setTicker("开始下载文件")//第一次通知时，显示在actionbar上面的内容
                .setContentTitle("正在下载QQ...") //内容设置标题
                .setContentText("请稍等...") //设置内容,如果内容中有!，在魅族手机中消息是弹不出来的
                .setWhen(System.currentTimeMillis()) //设置消息什么时候发送
                .setSmallIcon(R.mipmap.myheader)
                .setProgress(100, 0, false) //展示带有进度条的消息通知
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//设置消息的优先级
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                //建议魅族手机开启这个属性，否则如果信息中包括！号，无论是英文模式还是中文模式，都无法显示通知1容易发送不出去消息
                .setOngoing(true) //﻿ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .build();
        ;

        //通过NotificationManager发送消息
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1, notification);
        num = 0;
        //模拟下载安装
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (num++ < 100) {
                    SystemClock.sleep(200);
                    builder.setProgress(100, num, false);
                    builder.setContentText("已完成:" + num + "%");
                    if (num == 100) {
                        //自动结束
                        builder.setProgress(0, 0, true);
                        builder.setContentText("");
                        builder.setContentTitle("正在安装...");
                    }
                    ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1, builder.build());
                    if (num == 100) {
                        SystemClock.sleep(2000);
                        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancelAll();
                    }
                }
            }
        }).start();

    }
}
