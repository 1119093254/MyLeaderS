package com.multshows.Beans;

/**
 * Created by Administrator on 2016/10/12.
 */
public class Participant {
    String Id;//加密活动参与者表主键id
    String ActivityId;//加密活动id
    String SignMobile;//联系电话
    int SignTime;//报名时间
    String SignTimeStr;//报名时间字符格式
    int ResignTime;//取消报名时间
    String ResignTimeStr;//取消报名时间字符格式
    int State;////状态1已报名2已取消报名
    UserInfoTag UserInfo;//活动参与者信息
    int SignTimes;//报名活动次数

    public int getSignTime() {
        return SignTime;
    }

    public void setSignTime(int signTime) {
        SignTime = signTime;
    }

    public String getSignTimeStr() {
        return SignTimeStr;
    }

    public void setSignTimeStr(String signTimeStr) {
        SignTimeStr = signTimeStr;
    }

    public int getResignTime() {
        return ResignTime;
    }

    public void setResignTime(int resignTime) {
        ResignTime = resignTime;
    }

    public String getResignTimeStr() {
        return ResignTimeStr;
    }

    public void setResignTimeStr(String resignTimeStr) {
        ResignTimeStr = resignTimeStr;
    }

    public UserInfoTag getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(UserInfoTag userInfo) {
        UserInfo = userInfo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getActivityId() {
        return ActivityId;
    }

    public void setActivityId(String activityId) {
        ActivityId = activityId;
    }

    public String getSignMobile() {
        return SignMobile;
    }

    public void setSignMobile(String signMobile) {
        SignMobile = signMobile;
    }


    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getSignTimes() {
        return SignTimes;
    }

    public void setSignTimes(int signTimes) {
        SignTimes = signTimes;
    }
}
