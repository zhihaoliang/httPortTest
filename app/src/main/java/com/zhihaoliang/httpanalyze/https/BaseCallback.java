package com.zhihaoliang.httpanalyze.https;



import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haoliang on 2017/3/28.
 * email:zhihaoliang07@163.com
 */

public abstract class BaseCallback implements Callback {


    @Override
    public void onResponse(Call call, Response response) {
        if (response == null || (!response.isSuccessful())) {
            onFaile("网络连接失败");
            return;
        }

        String resp = (String) response.body();

        try {
            JSONObject jsonObject = new JSONObject(resp);
            if(jsonObject.getBoolean("success")){
                onSucess(resp);
            }else{
                onFaile(jsonObject.getString("errorCode"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onFaile("网络连接失败");
        }

    }

    @Override
    public void onFailure(Call call, Throwable t) {
        onFaile("网络连接失败");
    }


    public abstract void onSucess(String msg);

    public abstract void onFaile(String msg);
}
