package com.multshows.Beans;

import java.io.Serializable;

/**
 * 消息实体类
 */
public class Message implements Serializable{
    private String Id;//加密用户消息主键Id
    private int Type;//1平台消息 2提示信息 3私聊消息4订单消息5任务消息6好友消息7互动消息
    private String Content;//聊天内容
    private int RecordTime;//消息发送时间
    private String RecordTimeStr;//消息发送时间
    private UserBase Sender;//发送者基础信息
    private UserBase Receiver;//接收者基础信息
    private int IsRead;//是否阅读 0未阅读 1已阅读
    private Order Order;//订单id

    public Message() {
    }


    public Order getOrders() {
        return Order;
    }

    public void setOrders(Order orders) {
        Order = orders;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
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

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }
}
