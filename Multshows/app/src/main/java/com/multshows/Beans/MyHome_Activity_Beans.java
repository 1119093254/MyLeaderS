package com.multshows.Beans;

/**
 * 描述：活动界面bean
 * 作者：李萧萧
 * 时间：2016.9.27
 */
public class MyHome_Activity_Beans {
    String Id;//加密活动主键Id
    String starTime;//开始时间
    String stopTime;//结束时间
    String time;//发布时间
    int lookNum ;//浏览次数
    String  title;//活动标题
    String activityPic;//活动图片地址
    int State;//活动状态
    private int flag;//0头部布局，1内容布局
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public MyHome_Activity_Beans(String Id, String starTime, String stopTime, String time, int lookNum, String title, String activityPic, int State,int flag) {
        this.Id = Id;
        this.starTime = starTime;
        this.stopTime = stopTime;
        this.time = time;
        this.lookNum = lookNum;
        this.title = title;
        this.activityPic = activityPic;
        this.State=State;
        this.flag=flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLookNum() {
        return lookNum;
    }

    public void setLookNum(int lookNum) {
        this.lookNum = lookNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityPic() {
        return activityPic;
    }

    public void setActivityPic(String activityPic) {
        this.activityPic = activityPic;
    }

}
