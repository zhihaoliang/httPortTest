package com.zhihaoliang.httpanalyze;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by haoliang on 2017/3/31.
 * email:zhihaoliang07@163.com
 */

public class MyApplication extends Application{

    public static MyApplication sMyApplication;

    public HashMap<String,String> mHashMAp = new HashMap<>();

    public void onCreate() {
        super.onCreate();
        sMyApplication = this;
    }

    public String getDeviceId(){
        return "43";
    }

    public String getEncryptKey(){
        return null;
    }
}
