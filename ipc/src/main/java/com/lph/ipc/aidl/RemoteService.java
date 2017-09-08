package com.lph.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.lph.ipc.IPareclModelManager;
import com.lph.ipc.model.ParcelMode;

import java.util.ArrayList;
import java.util.List;

public class RemoteService extends Service {
    List<ParcelMode> mData;
    public RemoteService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mData = new ArrayList<>();
        mData.add(new ParcelMode("lph",20));
        mData.add(new ParcelMode("wtx",20));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new RemoteProxy();
    }

    class RemoteProxy extends IPareclModelManager.Stub{

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
    }
}
