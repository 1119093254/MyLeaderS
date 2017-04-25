package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 查询分类任务列表实体类
 */
public class TaskClass implements Serializable{
    private String Id;//加密Id
    private String Name;//任务分类名称
    private int Type;//1:任务 2:公益
    private String TypeName;//类型：分类名称
    private String RecordTime;//记录时间
    private List<TaskClassTag> TaskTagList;//此任务分类下的任务标签集合

    public TaskClass() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }

    public List<TaskClassTag> getTaskTagList() {
        return TaskTagList;
    }

    public void setTaskTagList(List<TaskClassTag> taskTagList) {
        TaskTagList = taskTagList;
    }
}
