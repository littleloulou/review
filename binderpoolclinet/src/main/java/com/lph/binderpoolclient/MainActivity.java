package com.lph.binderpoolclient;

import android.nfc.Tag;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lph.binderpoolserver.BinderPoolManager;
import com.lph.binderpoolserver.BinderRequestCode;
import com.lph.binderpoolserver.IBinderPool;
import com.lph.binderpoolserver.ICompute;
import com.lph.binderpoolserver.ISecurityCenter;

public class MainActivity extends AppCompatActivity {

    private BinderPoolManager mManager;
    private BinderPoolManager.CallBack mComputeCallBack;
    private BinderPoolManager.CallBack mSecurityCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = BinderPoolManager.newInstance(this);
    }

    public void bind(View view) {
        mComputeCallBack = new BinderPoolManager.CallBack() {
            @Override
            public void onServiceConnected(IBinder binder) {
                //然后通过Stub对象，拿到远程对象
                ICompute iCompute = ICompute.Stub.asInterface(binder);
                try {
                    int res = iCompute.add(1, 2);
                    Log.d("MainActivity", "remote result is " + res);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onServiceDisconnected(IBinderPool pool) {
                return false;
            }
        };
        mManager.bindService(new BinderRequestCode(BinderRequestCode.REQUEST_CODE_COMPUTE), mComputeCallBack);

        mSecurityCallBack = new BinderPoolManager.CallBack() {
            @Override
            public void onServiceConnected(IBinder binder) {
                ISecurityCenter iSecurityCenter = ISecurityCenter.Stub.asInterface(binder);
                try {
                    String encryptyStr = iSecurityCenter.encrypty("fdasgldsakjfds");
                    String decryptyStr = iSecurityCenter.decrypty(encryptyStr);
                    Log.d("MainActivity", "encryptyStr is " + encryptyStr);
                    Log.d("MainActivity", "decryptyStr is " + decryptyStr);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onServiceDisconnected(IBinderPool pool) {
                return false;
            }
        };
        mManager.bindService(new BinderRequestCode(BinderRequestCode.REQUEST_CODE_SECURYTIY), mSecurityCallBack);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManager.unbindService(mComputeCallBack);
        mManager.unbindService(mSecurityCallBack);
    }
}
