package com.multshows.Beans;

import java.io.Serializable;

/**
 * 好友列表 bean对象
 */
public class UserFriendTerm implements Serializable{
    private String UserId;//加密UserId
    //private String TargetUserId;//目标用户加密userId
    private String NickName;//用户昵称
    private int State;//好友申请状态  0 等待通过 1 通过申请 2 拒绝申请
    private int PageIndex;//页码
    private int PageSize;//每页显示条数
    private int IsNickName;//是否包括备注搜索:1:是0：不是

    public UserFriendTerm() {
    }

    public int getIsNickName() {
        return IsNickName;
    }

    public void setIsNickName(int isNickName) {
        IsNickName = isNickName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }
}
