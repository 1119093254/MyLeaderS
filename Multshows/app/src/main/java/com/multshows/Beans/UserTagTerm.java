package com.multshows.Beans;

import java.io.Serializable;

/**
 * 用户标签获取传参对象
 */
public class UserTagTerm implements Serializable{
    private String UserId;//加密UserId
    private int PageIndex;//页码
    private int PageSize;//每页显示条数

    public UserTagTerm() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }
}
