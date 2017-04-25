package com.multshows.Beans;

import java.io.Serializable;

/**
 * 聊天历史信息 实体类
 */
public class ChatLog implements Serializable{
    private String Id;//加密聊天记录主键Id
    private String SenderId;//加密发送者用户id
    private String ReceiverId;//加密接收者用户id
    private String Content;//聊天内容
    private int RecordTime;//消息发送时间
    private String RecordTimeStr;//消息发送时间
    private UserBase Sender;//发送者基础信息
    private UserBase Receiver;//接收者基础信息

    public ChatLog() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
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

    public String getRecordTimeStr() {
        return RecordTimeStr;
    }

    public void setRecordTimeStr(String recordTimeStr) {
        RecordTimeStr = recordTimeStr;
    }

    public UserBase getSender() {
        return Sender;
    }

    public void setSender(UserBase sender) {
        Sender = sender;
    }

    public UserBase getReceiver() {
        return Receiver;
    }

    public void setReceiver(UserBase receiver) {
        Receiver = receiver;
    }
}
