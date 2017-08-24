package com.zhihaoliang.httpanalyze.beans;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by zhihaoliang on 2017/6/3.
 * Email:zhihaoliang07@163.com
 */
@Root(name = "list", strict = false)
public class PropertyListBean {
    @ElementList(inline = true, entry = "property",required = false)
    public ArrayList<PropertyBean> property;
}
