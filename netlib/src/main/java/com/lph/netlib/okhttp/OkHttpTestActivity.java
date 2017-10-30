package com.lph.netlib.okhttp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lph.netlib.R;
import com.lph.netlib.okhttp.intercept.CommonInterceptor;
import com.lph.netlib.okhttp.intercept.REWRITE_CACHE_CONTROL_INTERCEPTOR;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpTestActivity extends AppCompatActivity {

    public final static String BaseURl = "http://192.168.0.105/test/";
    public final static String RequestFile = "demo.json";
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_test);
        okHttpClient = configOkHttpClient();

    }

    public OkHttpClient configOkHttpClient() {
        //缓存路径和大小
        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(10, TimeUnit.SECONDS)//读取超时
                .writeTimeout(10, TimeUnit.SECONDS)//写入超时
                .addInterceptor(new CommonInterceptor()) //添加公共参数
                .addInterceptor(interceptor)//添加日志拦截器
                .addNetworkInterceptor(new REWRITE_CACHE_CONTROL_INTERCEPTOR())//添加缓存拦截器
                .addInterceptor(new REWRITE_CACHE_CONTROL_INTERCEPTOR())//这句代码很有必要，否则断网的情况是获取不到缓存数据的
                .cache(cache)//把缓存添加进来
                .build();

    }

    public void request(View view) {
        final Request request = new Request.Builder().url(BaseURl + RequestFile).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("OkHttpTestActivity:" + response.body().string());
            }
        });
    }
}
