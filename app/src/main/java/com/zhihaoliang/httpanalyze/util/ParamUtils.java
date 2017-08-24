package com.zhihaoliang.httpanalyze.util;

import android.util.Base64;

import com.zhihaoliang.httpanalyze.MyApplication;

import java.io.UnsupportedEncodingException;
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

    public static HashMap<String, String> getParam(String svceName,String data,String dvcCode,String encryptKey ,boolean isOnlyXml) {
        HashMap<String, String> param = new HashMap<>();

        param.put("svceName", svceName);
        param.put("dvcCode", dvcCode); //其它接口都用激活后返回的设备ID---deviceId
        param.put("charset", "GBK");
        if(isOnlyXml){
            param.put("rtnType", "xml");
            param.put("type", "xml");
        }else{
            param.put("rtnType", "json");
            param.put("type", "json");
        }
        param.put("data", data);
        param.put("sign", sign(param, encryptKey));

        return param;
    }

    private static String sign(Map<String, String> params, String encryptKey) {
        String tempMd5 = createLinkString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
         tempMd5 = tempMd5 + encryptKey;

        //md5 加密
        tempMd5 = MD5Util.MD5Encode(tempMd5);
        //Base64加密
        byte[] dates = new byte[0];
        try {
            dates = tempMd5.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
