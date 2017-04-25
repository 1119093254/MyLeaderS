package com.multshows.Beans;

import java.io.Serializable;

/**
 * 会话列表数据 beans对象
 */
public class Chat_ConversationList_Beans implements Serializable {
    private String sendUserId;//发送消息的id
    private String sendUserName;//发送消息的昵称
    private String sendUserPic;//发送消息的头像
    private String sendLastMessage;//最后一条消息内容
    private String sendTime;//最后一条消息发送时间
    private int unReadNumber;//未读条数
    private int Type;//0私聊1群聊

    public Chat_ConversationList_Beans(String sendUserId, String sendUserName, String sendUserPic,
                                       String sendLastMessage, String sendTime, int unReadNumber,int Type) {
        this.sendUserId = sendUserId;
        this.sendUserName = sendUserName;
        this.sendUserPic = sendUserPic;
        this.sendLastMessage = sendLastMessage;
        this.sendTime = sendTime;
        this.unReadNumber = unReadNumber;
        this.Type=Type;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSendUserPic() {
        return sendUserPic;
    }

    public void setSendUserPic(String sendUserPic) {
        this.sendUserPic = sendUserPic;
    }

    public String getSendLastMessage() {
        return sendLastMessage;
    }

    public void setSendLastMessage(String sendLastMessage) {
        this.sendLastMessage = sendLastMessage;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getUnReadNumber() {
        return unReadNumber;
    }

    public void setUnReadNumber(int unReadNumber) {
        this.unReadNumber = unReadNumber;
    }

    @Override
    public String toString() {
        return "Chat_ConversationList_Beans{" +
                "sendUserId='" + sendUserId + '\'' +
                ", sendUserName='" + sendUserName + '\'' +
                ", sendUserPic='" + sendUserPic + '\'' +
                ", sendLastMessage='" + sendLastMessage + '\'' +
                ", sendTime=" + sendTime +
                ", unReadNumber=" + unReadNumber +
                '}';
    }
}
