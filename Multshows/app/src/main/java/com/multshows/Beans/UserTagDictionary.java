package com.multshows.Beans;

import java.io.Serializable;

/**
 * 兴趣标签实体类
 */
public class UserTagDictionary implements Serializable{
    private String Id;//加密Id
    private String TagName;//标签名称
    private int State;//兴趣标签： 1:启用 0:未启用
    private int RecordTime;//记录时间
    private String UserId;//用户UserId--加密
    private String RecordTimeStr;//记录时间(字符串)2016-09-22 11:07:59
    private int IsOwner;//用户UserId--加密

    public UserTagDictionary() {
    }

    public String getRecordTimeStr() {
        return RecordTimeStr;
    }

    public void setRecordTimeStr(String recordTimeStr) {
        RecordTimeStr = recordTimeStr;
    }

    public int getIsOwner() {
        return IsOwner;
    }

    public void setIsOwner(int isOwner) {
        IsOwner = isOwner;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
