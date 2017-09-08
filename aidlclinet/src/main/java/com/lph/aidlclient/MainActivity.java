package com.lph.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.lph.ipc.IPareclModelManager;
import com.lph.ipc.model.ParcelMode;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MyConnection mServerConn = new MyConnection();
    private IPareclModelManager mManager;
    private final static String TAG = "MainActivity_Messenger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connect(View view) {

        /*傻逼魅族，在A应用里使用 bindService 启动另一个应用B的Service的时候一直启动不起来
        解决方法：权限--后台管理——将智能后台改为允许后台管理*/

        //如果是魅族手机需要将服务端程序允许后台运行设置成允许，而不是智能
        Intent service = new Intent();
        service.setAction("com.lph.ipc.IPareclModelManager");
        //android 5.0 系统要求启动远程service需要使用显示intent
        service.setPackage("com.lph.ipc");
        boolean res = bindService(service, mServerConn, BIND_AUTO_CREATE);
        Log.e("MainActivity", res + "");
    }

    public void get(View view) {
        try {
            List<ParcelMode> modes = mManager.getModes();
            System.out.println("MainActivity" + modes);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void add(View view) {
        try {
            mManager.addModel(new ParcelMode(SystemClock.currentThreadTimeMillis() + "", new Random().nextInt()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mManager = IPareclModelManager.Stub.asInterface(iBinder);
            if (mManager != null) {
                Log.d("MainActivity", "server connected success");
            } else {
                Log.d("MainActivity", "server connected failure");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("MainActivity", "service disconnect");
        }

        @Override
        public void onBindingDied(ComponentName name) {

        }

    }

    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        System.out.println(String.format("MainActivity:packageName:%s,className:%s", packageName, className));
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    /*################################################### Messenger ##############################*/

    private static final int FROM_CLIENT_GETBASE = 1;
    private static final int FROM_CLIENT_GETSTRING = 2;
    private static final int FROM_CLIENT_GETOBJ = 3;

    public void MessagerDemo(View view) {
        Intent service = new Intent("com.lph.ipc.MessagerService");
        service.setPackage("com.lph.ipc");
        mMessengerConnection = new MessengerConnection();
        bindService(service, mMessengerConnection, BIND_AUTO_CREATE);
    }

    private Messenger mMessenger;
    private MessengerConnection mMessengerConnection;
    //这两个字段在客户端使用，目的接受服务端发送的消息.
    private static MessengerServerHandler mMessengerHandler = new MessengerServerHandler();
    private Messenger mHandelServerMsg = new Messenger(mMessengerHandler);


    private class MessengerConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //通过远程对象创建一个Messager对象
            mMessenger = new Messenger(iBinder);
            //获取一个用来传递消息的message对象
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putParcelable("client", new ParcelMode("client", 18));
            bundle.putString("who", "from client");
            msg.setData(bundle);
            msg.what = 0;
            //这里初始化这个字段，在服务端使用它向客户端发送消息
            msg.replyTo = mHandelServerMsg;
            try {
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }


    //这里用来处理服务端响应的值
    private static class MessengerServerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FROM_CLIENT_GETBASE:
                    int baseData = msg.getData().getInt("baseData");
                    Log.i(TAG, "from server:" + baseData);
                    break;
                case FROM_CLIENT_GETSTRING:
                    String stringData = msg.getData().getString("stringData");
                    Log.i(TAG, "from server:" + stringData);
                    break;
                case FROM_CLIENT_GETOBJ:
                    msg.getData().setClassLoader(getClass().getClassLoader());
                    ParcelMode objData =  msg.getData().getParcelable("objData");
                    Log.i(TAG, "from server:" + objData);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    //通过这三个不同的请求类型，服务端返回不同的值

    public void base(View view) {
        if (mMessenger != null) {
            clientSendMsg(FROM_CLIENT_GETBASE);
        }
    }



    public void string(View view) {
        if (mMessenger != null) {
            clientSendMsg(FROM_CLIENT_GETSTRING);
        }
    }

    public void obj(View view) {
        if (mMessenger != null) {
                clientSendMsg(FROM_CLIENT_GETOBJ);
        }
    }

    private void clientSendMsg(int what) {
        Message msg = Message.obtain(null, what);
        try {
            msg.replyTo = mHandelServerMsg;
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mManager!=null){
            unbindService(mServerConn);
        }

        if (mMessengerConnection != null && mMessenger!=null) {
            unbindService(mMessengerConnection);
        }
    }
}
