package com.multshows.Beans;

import java.io.Serializable;

/**
 * 展示内容
 */
public class ChatContext_Beans implements Serializable{
    private String uid;//用户id
    private String nickName;//用户昵称
    private String userPicUrl;//用户头像
    private String context;//消息内容
    private boolean message_Flag;//消息类型
    private String time;//发送时间
    private int flag;//布局标志位(0本人;1他人)

    public ChatContext_Beans(String uid,String nickName, String userPicUrl, String context, boolean message_Flag, String time, int flag) {
        this.uid = uid;
        this.nickName=nickName;
        this.userPicUrl = userPicUrl;
        this.context = context;
        this.message_Flag = message_Flag;
        this.time = time;
        this.flag = flag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isMessage_Flag() {
        return message_Flag;
    }

    public void setMessage_Flag(boolean message_Flag) {
        this.message_Flag = message_Flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
