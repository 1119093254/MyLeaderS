package com.multshows.Beans;

import java.io.Serializable;

/**
 * 描述：省市区对象
 * 作者：贾强胜
 * 时间：2016.8.30
 */
public class DictAddress implements Serializable{
    private String Id;
    private String FullName;

    public DictAddress() {
    }

    public DictAddress(String id, String fullName) {
        Id = id;
        FullName = fullName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    @Override
    public String toString() {
        return "DictAddress{" +
                "Id='" + Id + '\'' +
                ", FullName='" + FullName + '\'' +
                '}';
    }
}
