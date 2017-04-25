package com.multshows.Beans;

import java.io.Serializable;

/**
 * 获取用户收货地址列表参数
 */
public class UserAddressTerm implements Serializable{
    private String UserId;//加密UserId
    private int IsDefault;//0 不默认 1 默认 -1全部
    private int PageIndex;//页码
    private int PageSize;//每页显示条数

    public UserAddressTerm(String userId, int isDefault, int pageIndex, int pageSize) {
        UserId = userId;
        IsDefault = isDefault;
        PageIndex = pageIndex;
        PageSize = pageSize;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int isDefault) {
        IsDefault = isDefault;
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
