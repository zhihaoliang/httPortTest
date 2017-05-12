package com.zhihaoliang.httpanalyze.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/4/10.
 * email:zhihaoliang07@163.com
 */
@Root(name = "portal", strict = false)
public class PortalBean {
    @Element(name = "name")
    private String name;

    @Element(name = "nameData")
    private String nameData;

    @Element(name = "isOnlyXml",required = false)
    private boolean isOnlyXml;

    @Element(name = "dis")
    private String dis;

    @Element(name = "url",required = false)
    private String url;

    @Element(name = "dvcCode")
    private String  dvcCode;

    @Element(name = "encryptKey")
    private String encryptKey;

    @ElementList(inline = true, entry = "property",required = false)
    public ArrayList<PropertyBean> property;

    @ElementList(inline = true, entry = "constant",required = false)
    private ArrayList<String> constant;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public ArrayList<PropertyBean> getProperty() {
        return property;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameData() {
        return nameData;
    }

    public String getDvcCode() {
        return dvcCode;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public boolean isOnlyXml() {
        return isOnlyXml;
    }

    public ArrayList<String> getConstant() {
        return constant;
    }
}
