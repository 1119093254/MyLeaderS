package com.multshows.Beans;

/**
 * 获取相册列表请求参数
 */
public class UserAlbumTerm {
    String UserId;//加密UserId
    int SoType;//可见返回 -1 全部 0 全部可见 1 好友可见2 本人可见
    int PageIndex;//页码
    int PageSize;//每页显示条数

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getSoType() {
        return SoType;
    }

    public void setSoType(int soType) {
        SoType = soType;
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

    public UserAlbumTerm() {
    }
}
