package com.multshows.Beans;

/**
 * 描述：任务完成情况列表bean
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class TaskList_Beans {
    String mImagString;//任务图片
    String mTaskTitle;//任务名称
    String mTaskReward;//任务奖励
    Boolean  mComplete;//是否完成
    String mTime;//完成任务时间

    public TaskList_Beans(String imagString, String taskTitle, String taskReward, Boolean complete, String time) {
        mImagString = imagString;
        mTaskTitle = taskTitle;
        mTaskReward = taskReward;
        mComplete = complete;
        mTime = time;
    }

    public String getImagString() {
        return mImagString;
    }

    public void setImagString(String imagString) {
        mImagString = imagString;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskReward() {
        return mTaskReward;
    }

    public void setTaskReward(String taskReward) {
        mTaskReward = taskReward;
    }

    public Boolean getComplete() {
        return mComplete;
    }

    public void setComplete(Boolean complete) {
        mComplete = complete;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }
}
