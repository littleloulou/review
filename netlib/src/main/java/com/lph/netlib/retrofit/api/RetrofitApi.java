package com.lph.netlib.retrofit.api;

import com.lph.netlib.retrofit.RetrofitTestActivity;
import com.lph.netlib.retrofit.service.GetTranslationService;
import com.lph.netlib.retrofit.model.Translation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lph on 2017/10/10.
 */

public class RetrofitApi {

    private final Retrofit mRetrofit;

    public RetrofitApi() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(RetrofitTestActivity.BASE_URL_JIN_3)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getTranslation() {
        //通过retrofit创建一个动态代理对象
        GetTranslationService getTranslationService = mRetrofit.create(GetTranslationService.class);
        //通过代理对象执行网络请求方法,得到一个转换对象
        Call<Translation> callTranslation = getTranslationService.getTranslation();
        //通过调用该对象的异步或者同步方法,拿到服务端返回结果
        //同步方式
     /*   try {
            Response<Translation> execute = callTranslation.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //异步方式：

        callTranslation.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                System.out.println("RetrofitApi:getTranslation: success");
                response.body().show();
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                System.out.println("RetrofitApi:getTranslation: failure");
                t.printStackTrace();
            }
        });
    }
}
