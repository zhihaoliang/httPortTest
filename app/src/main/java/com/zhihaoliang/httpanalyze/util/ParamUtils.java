package com.zhihaoliang.httpanalyze.util;

import android.util.Base64;

import com.zhihaoliang.httpanalyze.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoliang on 2017/3/28.
 * email:zhihaoliang07@163.com
 */

public class ParamUtils {

    public static HashMap<String, String> getParam(String svceName,String data) {
        HashMap<String, String> param = new HashMap<>();

        String deviceId = MyApplication.sMyApplication.getDeviceId();
        String dvcCode = DeviceUtils.getDeviceIMSI(MyApplication.sMyApplication);

        param.put("svceName", svceName);
        if (svceName.equals("activate")) {
            param.put("dvcCode", dvcCode); //激活接口用乾坤服务方提供的指定的dvcCode
        } else {
            param.put("dvcCode", deviceId); //其它接口都用激活后返回的设备ID---deviceId
        }
        param.put("charset", "GBK");
        param.put("rtnType", "json");
        param.put("type", "json");
        param.put("data", data);
        param.put("sign", sign(param, deviceId, dvcCode));

        return param;
    }

    private static String sign(Map<String, String> params, String deviceId, String dvcCode) {
        String tempMd5 = createLinkString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        // 加密串最后要加上密钥串
        switch (params.get("svceName")) {
            case "activate":
                tempMd5 = tempMd5 + dvcCode; // 激活接口的密钥串=设备编号
                break;
            case "login":
            case "forgetPwd":
                if (deviceId == null) {
                    deviceId = "";
                }
                tempMd5 = tempMd5 + deviceId;
                break;
            default:
                String encryptKey = MyApplication.sMyApplication.getEncryptKey();
                if (encryptKey == null) {
                    encryptKey = "";
                }
                tempMd5 = tempMd5 + encryptKey;
                break;
        }

        //md5 加密
        tempMd5 = MD5Util.MD5Encode(tempMd5);
        //Base64加密
        byte[] dates = tempMd5.getBytes();
        return  Base64.encodeToString(dates,Base64.NO_WRAP);
    }


    private static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {
                // 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
}
