package com.lph.binderpoolserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lph on 2017/9/14.
 */

public class BinderPoolManager {
    private static final String TAG = "BinderPoolManager";
    private static BinderPoolManager mInstance;
    private RemoteServerConnection mServerConnection = new RemoteServerConnection();
    private BinderRequestCode mRequestCode = null;

    private HashMap<CallBack, Integer> mCallBacks = new HashMap<>();

    //远程服务挂掉的时候，重新连接远程服务
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mIBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mIBinderPool = null;
            bindService(mRequestCode, mCallBack);
        }
    };


    private static Context mConext;
    private IBinderPool mIBinderPool;
    private CountDownLatch mCountDownLatch;
    private CallBack mCallBack;

    public static BinderPoolManager newInstance(Context context) {
        if (mInstance == null) {
            synchronized (BinderPoolManager.class) {
                if (mInstance == null) {
                    mInstance = new BinderPoolManager(context);
                }
            }
        }
        return mInstance;
    }

    private BinderPoolManager(Context ctx) {
        mConext = ctx;
    }

    public synchronized void bindService(BinderRequestCode requestCode, CallBack callBack) {
        mRequestCode = requestCode;
        mCallBack = callBack;
        if (!mCallBacks.containsKey(callBack)) {
            mCallBacks.put(callBack, requestCode.getValue());
        }
        //此时说明远程服务已经连接过了，可以直接使用，获取
        if (mIBinderPool != null && mIBinderPool.asBinder().isBinderAlive()) {
            if (callBack != null && requestCode != null) {
                //先通过manager的方法得到远程的binder对象
                IBinder binder = queryBinder(requestCode);
                callBack.onServiceConnected(binder);
                return;
            }

        }
        Intent intent = new Intent("com.lph.bindpool.service");
        intent.setPackage("com.lph.binderpoolserver");
        boolean isBind = mConext.bindService(intent, mServerConnection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "bind remote service is success ?  " + isBind);
    }


    private class RemoteServerConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIBinderPool = IBinderPool.Stub.asInterface(iBinder);
            try {
                mIBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "service connect success...");
            Set<Map.Entry<CallBack, Integer>> entries = mCallBacks.entrySet();
            for (Map.Entry entry :
                    entries) {
                CallBack callBack = (CallBack) entry.getKey();
                Integer requestCode = (Integer) entry.getValue();
                try {
                    IBinder binder = mIBinderPool.queryBinder(new BinderRequestCode(requestCode));
                    callBack.onServiceConnected(binder);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "service  disconnect ...");
            if (mCallBack != null) {
                //判断客户端是否需要自己处理，否则则有manager处理
                if (!mCallBack.onServiceDisconnected(mIBinderPool)) {

                }
            }
        }
    }

    public IBinder queryBinder(BinderRequestCode requestCode) {
        try {
            return mIBinderPool == null ? null : mIBinderPool.queryBinder(requestCode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void unbindService(CallBack callBack) {
        mCallBacks.remove(callBack);
        if (mCallBacks.size() == 0) {
            if (mIBinderPool != null && mIBinderPool.asBinder().isBinderAlive()) {
                mConext.unbindService(mServerConnection);
            }
            mIBinderPool = null;
            mConext = null;
            mInstance = null;
        }

    }

    /**
     * 远程服务连接失败或者是成功，在客户端的回调
     */
    public interface CallBack {
        void onServiceConnected(IBinder binder);

        boolean onServiceDisconnected(IBinderPool pool);
    }


}
