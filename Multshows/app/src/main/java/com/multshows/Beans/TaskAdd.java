package com.multshows.Beans;

import java.io.Serializable;

/**
 * 添加任务实体类
 */
public class TaskAdd implements Serializable{
    private String Id;//加密任务id
    private String ClassId;//加密分类id
    private String TagId;//加密分类标签id
    private String Imgs;//图片
    private int Type;//1任务 2公益
    private String Title;//任务标题
    private String Infos;//任务详情
    private int BeginTime;//开始时间
    private String BeginTimeStr;//开始时间字符串格式
    private int EndTime;//结束时间
    private String EndTimeStr;//结束时间字符串格式
    private double Reward;//奖励金额
    private int IsShow;//是否显示 1显示 0 不显示
    private String PublisherId;//加密发布者id
    private String ExecutorId;//加密执行者id
    private int AtchType;//附件类型 1图片 2视频
    private String VideoName;//视频名称
    private int IsDefined;//是否自定义任务 0不是 1是

    public TaskAdd() {
    }

    public int getIsDefined() {
        return IsDefined;
    }

    public void setIsDefined(int isDefined) {
        IsDefined = isDefined;
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

    public String getBeginTimeStr() {
        return BeginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        BeginTimeStr = beginTimeStr;
    }

    public int getEndTime() {
        return EndTime;
    }

    public void setEndTime(int endTime) {
        EndTime = endTime;
    }

    public String getEndTimeStr() {
        return EndTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        EndTimeStr = endTimeStr;
    }

    public double getReward() {
        return Reward;
    }

    public void setReward(double reward) {
        Reward = reward;
    }

    public int getIsShow() {
        return IsShow;
    }

    public void setIsShow(int isShow) {
        IsShow = isShow;
    }

    public String getPublisherId() {
        return PublisherId;
    }

    public void setPublisherId(String publisherId) {
        PublisherId = publisherId;
    }

    public String getExecutorId() {
        return ExecutorId;
    }

    public void setExecutorId(String executorId) {
        ExecutorId = executorId;
    }
}
