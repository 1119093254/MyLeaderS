package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：活动详情实体类
 * 作者：李萧萧
 * 时间：2016.10.12
 */
public class Activity implements Serializable{
    /**
     * Sponsor : {"AccountId":"2Oj7","LoginName":"呵呵","LoginPassword":"3254234","TogglePassword":"123543","Weibo":"3@yahoo.com.cn","QQ":"1101201","ListUserTag":[],"SeenMeCount":0,"ReadMyCount":0,"RewardAmount":5.45,"UserId":"2O1P","Birthday":"1981-10-31T23:49:18+08:00","Sex":1,"NickName":"漂亮妈妈","RealName":"刘涛","Portrait":"11","Signature":"漂亮妈妈","IsConfirm":1,"State":1,"UserType":0,"ParentId":0,"Flowers":0,"Balance":0,"AbsenceTimes":0,"Mobile":"15555555555","PopularizeId":0}
     * Views : 222
     * ParticipantCount : 0
     * Participant : []
     * Id : 2O5T
     * Title : 活动2
     * Location : 邻里中心
     * Infos : 打牌
     * SignDeadline : 2033-11-30T07:35:45+08:00
     * BeginTime : 2033-11-30T07:35:45+08:00
     * EndTime : 2033-11-30T07:35:45+08:00
     * State : 2
     * IsShow : true
     */
    private String Id;//加密活动主键Id
    private String Title;//活动标题
    private String Location;////活动地点
    private String Infos;//活动描述
    String Imgs;//活动图片
    private int SignDeadline;//报名截止时间
    private int BeginTime;//活动开始时间
    private int EndTime;//活动结束时间
    String SignDeadlineStr;//报名截止日期字符格式
    String BeginTimeStr;//活动开始时间字符格式
    String EndTimeStr;//活动结束时间字符格式
    private int State;//活动状态
    private boolean IsShow;//宝宝可看标识位
    private UserInfoTag Sponsor;//活动发起者信息
    private List<Participant> Participant;//报名人信息
    int ParticipantCount;//已报名人数
    int RecordTime;//记录时间
    String RecordTimeStr;//记录发布时间字符格式
    int Views;//活动浏览量
    List<ShowsCommentUser> CommentUser;//评论的用户
    int CommentCount;//评论数量

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

    public String getSignDeadlineStr() {
        return SignDeadlineStr;
    }

    public void setSignDeadlineStr(String signDeadlineStr) {
        SignDeadlineStr = signDeadlineStr;
    }

    public String getBeginTimeStr() {
        return BeginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        BeginTimeStr = beginTimeStr;
    }

    public String getEndTimeStr() {
        return EndTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        EndTimeStr = endTimeStr;
    }

    public boolean isShow() {
        return IsShow;
    }

    public void setShow(boolean show) {
        IsShow = show;
    }

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }

    public String getRecordTimeStr() {
        return RecordTimeStr;
    }

    public void setRecordTimeStr(String recordTimeStr) {
        RecordTimeStr = recordTimeStr;
    }

    public UserInfoTag getSponsor() {
        return Sponsor;
    }

    public void setSponsor(UserInfoTag sponsor) {
        Sponsor = sponsor;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int Views) {
        this.Views = Views;
    }

    public int getParticipantCount() {
        return ParticipantCount;
    }

    public void setParticipantCount(int ParticipantCount) {
        this.ParticipantCount = ParticipantCount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getInfos() {
        return Infos;
    }

    public void setInfos(String Infos) {
        this.Infos = Infos;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public boolean isIsShow() {
        return IsShow;
    }

    public void setIsShow(boolean IsShow) {
        this.IsShow = IsShow;
    }

    public List<Participant> getParticipant() {
        return Participant;
    }

    public void setParticipant(List<Participant> Participant) {
        this.Participant = Participant;
    }

    public String getImgs() {
        return Imgs;
    }

    public void setImgs(String imgs) {
        Imgs = imgs;
    }

    public List<ShowsCommentUser> getCommentUser() {
        return CommentUser;
    }

    public void setCommentUser(List<ShowsCommentUser> commentUser) {
        CommentUser = commentUser;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }
}
