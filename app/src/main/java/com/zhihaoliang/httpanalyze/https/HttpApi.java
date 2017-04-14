package com.zhihaoliang.httpanalyze.https;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by haoliang on 2017/3/28.
 * email:zhihaoliang07@163.com
 */

public class HttpApi {

    //GsonConverterFactory
    //ScalarsConverterFactory

    public static synchronized ApiService ApiTypeService(String url) {
       return ApiTypeService(url,GsonConverterFactory.create());
    }
    public static synchronized ApiService ApiTypeService(String url, Converter.Factory factory) {
        OkHttpClient httpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .build();
        ApiService mApiTypeService = retrofit.create(ApiService.class);
        return mApiTypeService;
    }

}
