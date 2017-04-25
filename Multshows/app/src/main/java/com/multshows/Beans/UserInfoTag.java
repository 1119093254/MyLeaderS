package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */
public class UserInfoTag implements Serializable{
    String UserId;//用户Id
    String NickName;//昵称
    String Portrait;//头像图片
    List<UserTag> UserTagList;//用户兴趣标签

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
