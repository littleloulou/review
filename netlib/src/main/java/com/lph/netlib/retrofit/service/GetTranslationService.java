package com.lph.netlib.retrofit.service;

import com.lph.netlib.retrofit.model.Translation;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lph on 2017/10/10.
 */

public interface GetTranslationService {
    //retrofit 通过此动态代理，读取接口中方法的注解，动态生成http请求
    //GET标记的请求
    //GET注解用来标记请求的方法，后面是请求的参数
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getTranslation();
}
