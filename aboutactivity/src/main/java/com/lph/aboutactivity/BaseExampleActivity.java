package com.lph.aboutactivity;


import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by lph on 2017/9/2.
 */

public class BaseExampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(getClass().getCanonicalName(),"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(getClass().getCanonicalName(),"onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w(getClass().getCanonicalName(),"onRestoreInstanceState");
        //activity异常终止的时候，会调用此方法，保存当前acitvity的状态
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.w(getClass().getCanonicalName(),"onSaveInstanceState");
        //activity异常终止的重新创建的时候，可以在这里读取异常终止时保存的状态
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(getClass().getCanonicalName(),"onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(getClass().getCanonicalName(),"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(getClass().getCanonicalName(),"onPuase");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(getClass().getCanonicalName(),"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(getClass().getCanonicalName(),"onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w(getClass().getCanonicalName(),"onConfigurationChanged");
    }
}
