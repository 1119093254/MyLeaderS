package com.multshows.Beans;

import java.io.Serializable;

/**
 *添加群聊消息
 */
public class GroupChatLogBase implements Serializable{
    private String UserId;//加密发送者用户id
    private String RCGroupId;//融云提供的群id
    private String Content;//聊天内容
    private int RecordTime;//消息发送时间

    public GroupChatLogBase() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getRCGroupId() {
        return RCGroupId;
    }

    public void setRCGroupId(String RCGroupId) {
        this.RCGroupId = RCGroupId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }
}
