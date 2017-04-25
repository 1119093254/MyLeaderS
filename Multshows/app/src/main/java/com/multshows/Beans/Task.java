package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 任务实体类   .
 */
public class Task implements Serializable{
    private String Id;//加密任务id
    private String ClassId;//加密分类id
    private String TagId;//加密分类标签id
    private int Type;//1任务 2公益
    private String Title;//任务标题
    private String Infos;//任务详情
    private int BeginTime;//开始时间
    private int EndTime;//结束时间
    private int State;//任务状态 0全部 1 未完成 2已完成 3 已确认
    private int RewardState;//任务奖励发放状态  0 未发放 1已发放
    private double Reward;//奖励金额
    private int RecordTime;//记录时间（添加时间）
    private int ConfirmTime;//确认时间
    private int IsShow;//是否显示 1显示 0 不显示
    private UserBase Publisher;//发布者信息
    private UserBase Executor;//执行者信息
    private String ClassName;//任务分类名
    private String TagName;//任务分类标签名
    private String Imgs;//图片
    private String Icon;//背景图
    String RecordTimeStr;//发布时间戳
    List<UserHead> LikeUser;//给公益掌声的用户
    int LikeCount;//掌声数量
    List<ShowsCommentUser> CommentUser;//评论公益的用户
    int CommentCount;//评论数量
    private List<UserHead> CompleteTaskUser;//已完成任务的用户
    private int CompleteCount;//已完成任务的用户数量
    private int AtchType;//附件类型 1图片 2视频
    private String VideoName;//视频名称
    public Task() {
    }

    public List<UserHead> getCompleteTaskUser() {
        return CompleteTaskUser;
    }

    public void setCompleteTaskUser(List<UserHead> completeTaskUser) {
        CompleteTaskUser = completeTaskUser;
    }

    public String getRecordTimeStr() {
        return RecordTimeStr;
    }

    public void setRecordTimeStr(String recordTimeStr) {
        RecordTimeStr = recordTimeStr;
    }

    public int getCompleteCount() {
        return CompleteCount;
    }

    public void setCompleteCount(int completeCount) {
        CompleteCount = completeCount;
    }

    public int getAtchType() {
        return AtchType;
    }

    public void setAtchType(int atchType) {
        AtchType = atchType;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public List<UserHead> getLikeUser() {
        return LikeUser;
    }

    public void setLikeUser(List<UserHead> likeUser) {
        LikeUser = likeUser;
    }

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int likeCount) {
        LikeCount = likeCount;
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

    public String getImgs() {
        return Imgs;
    }

    public void setImgs(String imgs) {
        Imgs = imgs;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
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

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getRewardState() {
        return RewardState;
    }

    public void setRewardState(int rewardState) {
        RewardState = rewardState;
    }

    public double getReward() {
        return Reward;
    }

    public void setReward(double reward) {
        Reward = reward;
    }

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }

    public int getConfirmTime() {
        return ConfirmTime;
    }

    public void setConfirmTime(int confirmTime) {
        ConfirmTime = confirmTime;
    }

    public int getIsShow() {
        return IsShow;
    }

    public void setIsShow(int isShow) {
        IsShow = isShow;
    }

    public UserBase getPublisher() {
        return Publisher;
    }

    public void setPublisher(UserBase publisher) {
        Publisher = publisher;
    }

    public UserBase getExecutor() {
        return Executor;
    }

    public void setExecutor(UserBase executor) {
        Executor = executor;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }
}
