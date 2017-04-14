package com.zhihaoliang.httpanalyze.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by haoliang on 2017/4/10.
 * email:zhihaoliang07@163.com
 */

@Root(name = "property", strict = false)
public class PropertyBean {
    @Element(name = "name")
    private String name;

    @Element(name = "value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
