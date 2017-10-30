package com.lph.netlib;

import android.app.Application;
import android.content.Context;

/**
 * Created by lph on 2017/10/30.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

}
