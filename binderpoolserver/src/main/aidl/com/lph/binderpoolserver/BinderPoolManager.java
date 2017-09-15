package com.lph.binderpoolserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by lph on 2017/9/14.
 */

public class BinderPoolManager {
    private static BinderPoolManager mInstance;

    private RemoteServerConnection mServerConnection = new RemoteServerConnection();

    private static Context mConext;
    private IBinderPool mIBinderPool;

    public BinderPoolManager newInstance(Context context) {
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

    public void bindService() {
        Intent intent = new Intent("com.lph.bindpool.service");
        intent.setPackage("com.lph.binderpoolserver");
        mConext.bindService(intent, mServerConnection, Context.BIND_AUTO_CREATE);
    }


    private class RemoteServerConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIBinderPool = IBinderPool.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

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

    public void unbindService() {
        mConext.unbindService(mServerConnection);
    }
}
