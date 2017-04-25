package com.multshows.Beans;

import java.io.Serializable;

/**
 * 消息界面——外部提示消息
 */
public class MessageData implements Serializable{
    private int Number;//未读数量
    private int Type;//1平台消息 2提示信息 3私聊消息4订单消息5任务消息6好友消息7互动消息
    private String Content;//聊天内容
    private int RecordTime;//消息发送时间
    private String RecordTimeStr;//消息发送时间
    private Order Order;//订单信息
    private int MsgType;//互动消息类型：1评论 2点赞3打赏4鲜花

    public MessageData() {
    }

    public Order getOrder() {
        return Order;
    }

    public void setOrder(Order order) {
        Order = order;
    }

    public int getMsgType() {
        return MsgType;
    }

    public void setMsgType(int msgType) {
        MsgType = msgType;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
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
}
