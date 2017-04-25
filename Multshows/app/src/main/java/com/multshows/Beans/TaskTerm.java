package com.multshows.Beans;

import java.io.Serializable;

/**
 * 获取任务列表  参数实体类
 */
public class TaskTerm implements Serializable{
    private int Type;//1任务 2公益
    private String ClassId;//加密任务分类id
    private String TagId;//加密任务分类标签id
    private int State;//1 未完成 2已完成 3 已确认
    private String Title;//标题
    private String Publisher;//发布者
    private String PublisherId;//加密发布者id
    private String Executor;//执行者
    private String ExecutorId;//加密执行者id
    private int PageIndex;//页码
    private int PageSize;//每页显示条数

    public TaskTerm() {
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
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

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getPublisherId() {
        return PublisherId;
    }

    public void setPublisherId(String publisherId) {
        PublisherId = publisherId;
    }

    public String getExecutor() {
        return Executor;
    }

    public void setExecutor(String executor) {
        Executor = executor;
    }

    public String getExecutorId() {
        return ExecutorId;
    }

    public void setExecutorId(String executorId) {
        ExecutorId = executorId;
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
