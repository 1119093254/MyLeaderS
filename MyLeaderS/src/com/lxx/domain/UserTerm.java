package com.lxx.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取宝宝列表 参数实体类
 */
public class UserTerm implements Serializable{
    private String UserId;//加密UserId
    private String NickName;//昵称
    private int BeginTime;//注册开始时间
    private int EndTime;//注册结束时间
    private int PageIndex;//页码
    private int PageSize;//每页显示条数
    private String UIds;//用户集合ids，用逗号隔开的加密id
    private int IsSort;//是否按照uids条件的顺序直接返回集合：1：是，0：否
    private String Mobile;//手机号查询
    private String CurrUserId;//当前登录的用户id--加密


    public UserTerm() {
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCurrUserId() {
        return CurrUserId;
    }

    public void setCurrUserId(String currUserId) {
        CurrUserId = currUserId;
    }

    public String getUIds() {
        return UIds;
    }

    public void setUIds(String UIds) {
        this.UIds = UIds;
    }

    public int getIsSort() {
        return IsSort;
    }

    public void setIsSort(int isSort) {
        IsSort = isSort;
    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public int getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(int beginTime) {
        BeginTime = beginTime;
    }

    public int getEndTime() {
        return EndTime;
    }

    public void setEndTime(int endTime) {
        EndTime = endTime;
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
