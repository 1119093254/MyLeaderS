package com.multshows.Beans;


import java.io.Serializable;
import java.util.List;

/**
 * 描述：所有发布任务名称列表bean(妈妈端可发布任务列表)
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class Task_List_Bean implements Serializable{
    private TaskClassTag mClassTag;//子标签实体
    private String className;//父标签名称
    private int flag;//是否显示父标签名称

    public Task_List_Bean(TaskClassTag classTag, String className, int flag) {
        mClassTag = classTag;
        this.className = className;
        this.flag = flag;
    }

    public TaskClassTag getClassTag() {
        return mClassTag;
    }

    public void setClassTag(TaskClassTag classTag) {
        mClassTag = classTag;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
