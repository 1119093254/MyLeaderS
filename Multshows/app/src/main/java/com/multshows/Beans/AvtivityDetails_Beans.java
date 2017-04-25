package com.multshows.Beans;

import java.io.Serializable;

/**
 * 描述：活动详情
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class AvtivityDetails_Beans implements Serializable{
    String username;//评论人姓名
    String replyusername;//被评论人姓名
    String content;//评论内容

    public AvtivityDetails_Beans(String username, String replyusername, String content) {
        this.replyusername = replyusername;
        this.username = username;
        this.content = content;
    }

    public String getReplyusername() {
        return replyusername;
    }

    public void setReplyusername(String replyusername) {
        this.replyusername = replyusername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}