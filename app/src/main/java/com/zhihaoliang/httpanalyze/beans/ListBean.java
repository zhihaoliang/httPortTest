package com.zhihaoliang.httpanalyze.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/4/10.
 * email:zhihaoliang07@163.com
 */
@Root(name = "root", strict = false)
public class ListBean {
    @ElementList(inline = true, entry = "portal")
    private ArrayList<PortalBean> portal;

    @Element(name = "baseUrl")
    private String baseUrl;

    public ArrayList<PortalBean> getPortal() {
        return portal;
    }

    public void setPortal(ArrayList<PortalBean> portal) {
        this.portal = portal;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
