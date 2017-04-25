package com.multshows.Beans;

import java.io.Serializable;

/**
 * 被送鲜花记录
 */
public class ShowsFlower implements Serializable{
    private String Id;//加密主键id
    private UserBase User;//用户信息
    private UserBase Author;//作者信息
    private ShowsBase Shows;//作品信息
    private int Amount;//鲜花数
    private int RecordTime;//送花时间
    private String RecordTimeStr;//送花时间对应字符格式

    public ShowsFlower() {
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

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
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
