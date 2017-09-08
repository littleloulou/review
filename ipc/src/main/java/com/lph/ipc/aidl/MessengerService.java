package com.lph.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.lph.ipc.model.ParcelMode;

/**
 * Created by lph on 2017/9/6.
 */

public class MessengerService extends Service{

    public static final int FROM_CLIENT = 0;
    public static final int FROM_CLIENT_GETBASE = 1;
    public static final int FROM_CLIENT_GETSTRING = 2;
    public static final int FROM_CLIENT_GETOBJ = 3;
    public static final String CLIENT_KEY = "client";

    private static Handler mMessagerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FROM_CLIENT:
                    msg.getData().setClassLoader(getClass().getClassLoader());
                    ParcelMode client_data = (ParcelMode) msg.getData().getParcelable(CLIENT_KEY);
                    String who = msg.getData().getString("who");
                    System.out.println("MessengerService:this data "+ who +" : "+ client_data);
                    break;
                case FROM_CLIENT_GETBASE:
                    //服务端接收到信息之后，回复客户端
                    Message msgBase = Message.obtain();
                    msgBase.what = FROM_CLIENT_GETBASE;
                    Bundle baseData = new Bundle();
                    baseData.putInt("baseData",1000);
                    msgBase.setData(baseData);
                    try {
                        msg.replyTo.send(msgBase);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case FROM_CLIENT_GETSTRING:
                    Message msgString =  Message.obtain();
                    msgString.what = FROM_CLIENT_GETSTRING;
                    Bundle strData = new Bundle();
                    strData.putString("stringData","from server response");
                    msgString.setData(strData);
                    try {
                        msg.replyTo.send(msgString);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case FROM_CLIENT_GETOBJ:
                    Message msgObj = Message.obtain();
                    Bundle objData = new Bundle();
                    msgObj.what = FROM_CLIENT_GETOBJ;
                    objData.putParcelable("objData",new ParcelMode("ServerobjData",18));
                    msgObj.setData(objData);
                    try {
                        msg.replyTo.send(msgObj);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //创建一个这兄弟，内部需要一个handler用来接收客户端发送的消息
    private Messenger mMessenger = new Messenger(mMessagerHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Messager内部封装了aidl的实现，可以直接获取到binder对象
        return mMessenger.getBinder();
    }

}
