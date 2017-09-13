package com.lph.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.lph.ipc.IPareclModelManager;
import com.lph.ipc.OnNewModeAddListener;
import com.lph.ipc.model.ParcelMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RemoteService extends Service {
    CopyOnWriteArrayList<ParcelMode> mData;
    AtomicBoolean mServiceIsAlive = new AtomicBoolean();
    //注册和注销远程接口，api提供这种机制
    RemoteCallbackList<OnNewModeAddListener> mListeners;

    public RemoteService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mData = new CopyOnWriteArrayList<>();
        mData.add(new ParcelMode("lph", 20));
        mData.add(new ParcelMode("wtx", 20));
        mListeners = new RemoteCallbackList<>();
        mServiceIsAlive.set(true);
        //每隔2秒刷新一条数据
        new Thread() {
            @Override
            public void run() {
                while (mServiceIsAlive.get()) {
                    SystemClock.sleep(2000);
//                    Prepare to start making calls to the currently registered callbacks.
                    int N = mListeners.beginBroadcast();
                    for (int i = 0; i < N; i++) {
//                    Retrieve an item in the active broadcast that was previously started with beginBroadcast().
                        OnNewModeAddListener broadcastItem = mListeners.getBroadcastItem(i);
                        try {
                            ParcelMode newModel = new ParcelMode("newAdd", (int) (SystemClock.currentThreadTimeMillis() / 100));
                            mData.add(newModel);
                            broadcastItem.OnNewsModeAdd(newModel);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
//                  Clean up the state of a broadcast previously initiated by calling beginBroadcast().
                   mListeners.finishBroadcast();
                }

            }
        }.start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new RemoteProxy();
    }

    class RemoteProxy extends IPareclModelManager.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<ParcelMode> getModes() throws RemoteException {

            return mData;
        }

        @Override
        public void addModel(ParcelMode mode) throws RemoteException {
            mData.add(mode);
        }

        @Override
        public void registerListener(OnNewModeAddListener lisenter) throws RemoteException {
//          Simple version of register(E, Object) that does not take a cookie object.
            boolean register = mListeners.register(lisenter);
            Log.e("RemoteService", String.format("register listener: %s is %b",lisenter,register));
        }

        @Override
        public void unregisterListener(OnNewModeAddListener lisenter) throws RemoteException {
//            Remove from the list a callback that was previously added with register(E).
            boolean unregister = mListeners.unregister(lisenter);
            Log.e("RemoteService", String.format("unregister listener: %s is %b",lisenter,unregister));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mServiceIsAlive.set(false);
    }
}
