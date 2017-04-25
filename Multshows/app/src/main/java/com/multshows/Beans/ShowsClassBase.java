package com.multshows.Beans;

import java.io.Serializable;

/**
 * 作品所属分类信息
 */
public class ShowsClassBase implements Serializable{
    private String Id;//加密shows分类id
    private String ClassName;//分类名称

    public ShowsClassBase(String id, String className) {
        Id = id;
        ClassName = className;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }
}
