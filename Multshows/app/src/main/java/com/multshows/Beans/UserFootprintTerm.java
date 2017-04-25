package com.multshows.Beans;

import java.io.Serializable;

/**
 * 获取用户足迹列表  参数实体
 */
public class UserFootprintTerm implements Serializable{
    private String UserId;//加密UserId
    private int Type;//1 我看过的 2看过我的
    private int PageIndex;//页码
    private int PageSize;//每页显示条数

    public UserFootprintTerm() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
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
