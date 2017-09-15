package com.lph.binderpoolserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.lph.binderpoolserver.BinderRequestCode;
import com.lph.binderpoolserver.IBinderPool;
import com.lph.binderpoolserver.ICompute;
import com.lph.binderpoolserver.ISecurityCenter;

public class QueryRemoteService extends Service {
    public QueryRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //返回的相当于一个dns服务对象，根据客户端的请求值，返回相应的binder对象
        return new BinderPoolProxy();
    }

    private class BinderPoolProxy extends IBinderPool.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public IBinder queryBinder(BinderRequestCode queryCode) throws RemoteException {
            IBinder binder = null;
            switch (queryCode.getValue()) {
                case BinderRequestCode.REQUEST_CODE_COMPUTE:
                    binder = new ComputeProxy();
                    break;
                case BinderRequestCode.REQUEST_CODE_SECURYTIY:
                    binder = new SecurityCenterProxy();
                    break;
                default:
                    binder = new ComputeProxy();

            }
            return binder;
        }
    }

    private class ComputeProxy extends ICompute.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    }


    private class SecurityCenterProxy extends ISecurityCenter.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String encrypty(String source) throws RemoteException {
            return source.toUpperCase();
        }

        @Override
        public String decrypty(String source) throws RemoteException {
            return source.toLowerCase();
        }
    }

}
