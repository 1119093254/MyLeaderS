package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 信息少的user对象
 */
public class User_ implements Serializable{
    private String UserId;//用户加密id
    private String NickName;//用户昵称
    private String Portrait;//用户头像
    private List<UserTag> UserTagList;//标签集合

    public User_(String userId, String nickName, String portrait, List<UserTag> userTagList) {
        UserId = userId;
        NickName = nickName;
        Portrait = portrait;
        UserTagList = userTagList;
    }

    public User_() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String portrait) {
        Portrait = portrait;
    }

    public List<UserTag> getUserTagList() {
        return UserTagList;
    }

    public void setUserTagList(List<UserTag> userTagList) {
        UserTagList = userTagList;
    }
}
