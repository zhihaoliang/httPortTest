package com.zhihaoliang.httpanalyze.https;



import com.zhihaoliang.httpanalyze.beans.ListBean;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by haoliang on 2017/3/28.
 * email:zhihaoliang07@163.com
 */

public interface ApiService {
    /**
     * 登陆接口
     */
    @FormUrlEncoded
    @POST
    Call<String> connact(@Url String url, @FieldMap HashMap<String, String> map);

    @GET("examples/frameweb/root.xml")
    Call<ListBean> initDate();
}
