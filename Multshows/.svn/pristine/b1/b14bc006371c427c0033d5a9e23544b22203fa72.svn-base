package com.multshows.Beans;

import java.io.Serializable;

/**
 * 描述：聊天消息实体类
 * 作者：贾强胜
 * 时间：2016.9.18
 */
public class ChatMessage_Beans implements Serializable{
    private int  messageID;//消息id
    private String content;//消息内容
    private String objectName;//消息对象名称
    private long receivedTime;//消息接收时间
    private long sentTime;//消息发送时间
    private String senderUserId;//发送消息的用户id
    private String targetId;//获取目标id
    private io.rong.imlib.model.Message.ReceivedStatus receivedStatus;//接收到的消息状态

    public ChatMessage_Beans(int messageID, String content, String objectName, long receivedTime, long sentTime,
                             String senderUserId, String targetId, io.rong.imlib.model.Message.ReceivedStatus receivedStatus) {
        this.messageID = messageID;
        this.content = content;
        this.objectName = objectName;
        this.receivedTime = receivedTime;
        this.sentTime = sentTime;
        this.senderUserId = senderUserId;
        this.targetId = targetId;
        this.receivedStatus = receivedStatus;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }

    public long getSentTime() {
        return sentTime;
    }

    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public io.rong.imlib.model.Message.ReceivedStatus getReceivedStatus() {
        return receivedStatus;
    }

    public void setReceivedStatus(io.rong.imlib.model.Message.ReceivedStatus receivedStatus) {
        this.receivedStatus = receivedStatus;
    }
}
