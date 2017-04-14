package com.zhihaoliang.httpanalyze.https;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.zhihaoliang.httpanalyze.MyApplication;
import com.zhihaoliang.httpanalyze.util.ParamUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by haoliang on 2017/3/28.
 * email:zhihaoliang07@163.com
 */

public class HttpConnact{

    public boolean doNet(String data,Callback callback) {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.sMyApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() == null) {
            return false;
        }
        if (!manager.getActiveNetworkInfo().isAvailable()) {
            return false;
        }
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator iterator = jsonObject.keys();
            String svceName = "";
            while(iterator.hasNext()){
                svceName = (String) iterator.next();
                break;
            }
            HashMap<String,String> hashMap = ParamUtils.getParam(svceName,data);
            ApiService apiService = HttpApi.ApiTypeService("11");
            Method method = ApiService.class.getDeclaredMethod(svceName, HashMap.class);
            Call  call = (Call) method.invoke(apiService,hashMap);
            call.enqueue(callback);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

}
