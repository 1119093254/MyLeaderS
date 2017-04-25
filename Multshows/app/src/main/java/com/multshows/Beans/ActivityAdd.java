package com.multshows.Beans;

import java.io.Serializable;

/**
 * 描述：添加活动实体类
 * 作者：李萧萧
 * 时间：2016.10.12
 */
public class ActivityAdd implements Serializable{
    String Id;//加密活动主键Id
    String SponsorId;//加密活动发起者id
    String Title;//活动标题
    String Infos;//活动描述
    String Imgs;//活动图片，多个字符隔开保存
    String Location;//活动地点
    int SignDeadline;//报名截止时间
    int BeginTime;//活动开始时间
    int EndTime;//活动结束时间
    int State;//活动状态
    Boolean IsShow;//宝宝可看标识位
    int RecordTime;//记录时间
    public ActivityAdd(String id, String sponsorId, String title, String infos,String imgs, String location, int signDeadline, int beginTime, int endTime, int state, Boolean isShow) {
        Id = id;
        SponsorId = sponsorId;
        Title = title;
        Infos = infos;
        Location = location;
        SignDeadline = signDeadline;
        BeginTime = beginTime;
        EndTime = endTime;
        State = state;
        IsShow = isShow;
        Imgs=imgs;
    }

    public ActivityAdd(String sponsorId, String title, String infos,String imgs, String location, int signDeadline, int beginTime, int endTime, int state, Boolean isShow) {
        SponsorId = sponsorId;
        Title = title;
        Infos = infos;
        Location = location;
        SignDeadline = signDeadline;
        BeginTime = beginTime;
        EndTime = endTime;
        State = state;
        IsShow = isShow;
        Imgs=imgs;
    }



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(String sponsorId) {
        SponsorId = sponsorId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInfos() {
        return Infos;
    }

    public void setInfos(String infos) {
        Infos = infos;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getImgs() {
        return Imgs;
    }

    public void setImgs(String imgs) {
        Imgs = imgs;
    }

    public int getSignDeadline() {
        return SignDeadline;
    }

    public void setSignDeadline(int signDeadline) {
        SignDeadline = signDeadline;
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

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public Boolean getShow() {
        return IsShow;
    }

    public void setShow(Boolean show) {
        IsShow = show;
    }
}
