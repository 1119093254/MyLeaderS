package com.multshows.Beans;

import java.io.Serializable;

/**
 * 点赞我的 返回列表数据
 */
public class ShowsLike implements Serializable{
    private String Id;//加密主键id
    private UserBase User;//点赞者信息
    private UserBase Author;//作者信息
    private ShowsBase Shows;//作品信息
    private int Type;//1 shows点赞 2照片点赞 3.公益点赞
    private int State;//0未点赞 1点赞

    public ShowsLike(String id, UserBase user, UserBase author, ShowsBase shows, int type, int state) {
        Id = id;
        User = user;
        Author = author;
        Shows = shows;
        Type = type;
        State = state;
    }

    public ShowsLike() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public UserBase getUser() {
        return User;
    }

    public void setUser(UserBase user) {
        User = user;
    }

    public UserBase getAuthor() {
        return Author;
    }

    public void setAuthor(UserBase author) {
        Author = author;
    }

    public ShowsBase getShows() {
        return Shows;
    }

    public void setShows(ShowsBase shows) {
        Shows = shows;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }
}
