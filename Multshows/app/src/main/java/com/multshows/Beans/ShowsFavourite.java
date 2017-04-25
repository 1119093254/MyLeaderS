package com.multshows.Beans;

import java.io.Serializable;

/**
 * 我的收藏列表实体类
 */
public class ShowsFavourite implements Serializable{
    private String Id;//加密主键id
    private UserBase User;//用户信息
    private UserBase Author;//作者信息
    private ShowsBase Shows;//作品信息
    private int RecordTime;//收藏时间
    private String RecordTimeStr;//收藏时间对应字符格式
    private int State;//0未收藏 1已收藏

    public ShowsFavourite() {
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

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }
}
