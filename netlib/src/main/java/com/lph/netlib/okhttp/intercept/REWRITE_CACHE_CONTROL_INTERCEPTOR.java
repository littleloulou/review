package com.lph.netlib.okhttp.intercept;

import android.util.Log;

import com.lph.netlib.MyApplication;
import com.lph.netlib.okhttp.util.NetUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.util.logging.Logger;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


import static android.content.ContentValues.TAG;

/**
 * Created by lph on 2017/10/30.
 */

public class REWRITE_CACHE_CONTROL_INTERCEPTOR implements Interceptor {

    public static final int AGE = 60;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        //如果网络不可用直接使用本地缓存
        if (!NetUtils.isConnected(MyApplication.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.d("Interceptor", "no net");
        }
        try {
            Response originalResponse = chain.proceed(request);

            //如果网络可用,缓存的有效时间是60秒
            //如果网络不可用，缓存的有效时间是好久好久
            if (NetUtils.isConnected(MyApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                //这里就统一配置了
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public,max-age=" + AGE)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        } catch (Exception ex) {
            //解决由于在有网络的情况下，服务端发生错误，加载本地缓存
            if (ex instanceof ConnectException) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                return chain.proceed(request).newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
            return null;
        }


    }
}
