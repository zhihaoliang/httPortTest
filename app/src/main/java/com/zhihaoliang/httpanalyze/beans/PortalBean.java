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

    @Element(name = "dis")
    private String dis;

    @Element(name = "url")
    private String url;

    @ElementList(inline = true, entry = "property")
    public ArrayList<PropertyBean> portal;

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

    public ArrayList<PropertyBean> getPortal() {
        return portal;
    }

    public void setPortal(ArrayList<PropertyBean> portal) {
        this.portal = portal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
