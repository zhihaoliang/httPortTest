package com.zhihaoliang.httpanalyze.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceUtils {

    public static String getDeviceId(Context ct){
        TelephonyManager tm = (TelephonyManager)  ct.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getDeviceIMSI(Context ct){
        TelephonyManager tm = (TelephonyManager)  ct.getSystemService(Context.TELEPHONY_SERVICE);
        return "460023367301591";
       // return tm.getSubscriberId();
    }

    public static String getNativePhoneNumber(Context ct) {
        String telNo = null;
        TelephonyManager tm = (TelephonyManager)  ct.getSystemService(Context.TELEPHONY_SERVICE);
        telNo= tm.getLine1Number();

        if ((telNo != null) && (telNo.length() > 11)){
            telNo = telNo.substring(3, telNo.length());
        }

        return telNo;
    }

}