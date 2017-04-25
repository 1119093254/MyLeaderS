package com.multshows.Beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 宝贝实体类
 */
public class BabyAPI implements Serializable{
    private String userId;//加密UserId
    private String Portrait;//头像
    private String NickName;//昵称
    private int Birthday;//生日
    private String BirthdayStr;//生日字符格式
    private int Sex;//性别 1男2女

    public BabyAPI() {
    }

    public String getBirthdayStr() {
        return BirthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        BirthdayStr = birthdayStr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String portrait) {
        Portrait = portrait;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public int getBirthday() {
        return Birthday;
    }

    public void setBirthday(int birthday) {
        Birthday = birthday;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }
}
