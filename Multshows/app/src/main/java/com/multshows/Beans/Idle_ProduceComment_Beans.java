package com.multshows.Beans;

import java.io.Serializable;

/**
 * 描述：闲置详情评论bean
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class Idle_ProduceComment_Beans implements Serializable{
    String id;//评论id
    String UserId;//评论id
    String username;//评论人姓名
    String replyUserId;//被评论人id
    String replyusername;//被评论人姓名
    String content;//评论内容
    String usetPic;//评论人头像地址
    int mTime;//评论发表时间

    public Idle_ProduceComment_Beans(String id,String UserId,String username, String replyUserId, String replyusername, String content, String usetPic, int time) {
        this.id=id;
        this.username = username;
        this.replyusername = replyusername;
        this.content = content;
        this.usetPic = usetPic;
        mTime = time;
        this.UserId=UserId;
        this.replyUserId=replyUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReplyusername() {
        return replyusername;
    }

    public void setReplyusername(String replyusername) {
        this.replyusername = replyusername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsetPic() {
        return usetPic;
    }

    public void setUsetPic(String usetPic) {
        this.usetPic = usetPic;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }
}
