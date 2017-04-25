package com.multshows.Beans;

/**
 * Created by Administrator on 2016/10/12.
 */
public class ActivityTerm {
    String CurrentUserId;//当前用户id
    String Title;//活动标题
    String Location;//活动地点
    String SponsorId;//加密活动发起者id
    String SponsorNickName;//活动发起者昵称
    String SponsorMobile;//发起人联系电话
    String UserId;//加密参与者id
    String UserNickName;//参与者昵称
    String UserMobile;//参与者手机号
    int IsShowExpire;//是否显示过期活动，0不显示 1显示
    int State;//活动状态 1: 进行中 2: 已结束 -1: 已取消
    int PageIndex;//页码
    int PageSize;//每页显示条数

    public int getIsShowExpire() {
        return IsShowExpire;
    }

    public void setIsShowExpire(int isShowExpire) {
        IsShowExpire = isShowExpire;
    }

    public String getCurrentUserId() {
        return CurrentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        CurrentUserId = currentUserId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(String sponsorId) {
        SponsorId = sponsorId;
    }

    public String getSponsorNickName() {
        return SponsorNickName;
    }

    public void setSponsorNickName(String sponsorNickName) {
        SponsorNickName = sponsorNickName;
    }

    public String getSponsorMobile() {
        return SponsorMobile;
    }

    public void setSponsorMobile(String sponsorMobile) {
        SponsorMobile = sponsorMobile;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserNickName() {
        return UserNickName;
    }

    public void setUserNickName(String userNickName) {
        UserNickName = userNickName;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
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
