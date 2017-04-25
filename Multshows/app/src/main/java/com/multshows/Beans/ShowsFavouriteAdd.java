package com.multshows.Beans;

import java.io.Serializable;

/**
 * 添加收藏作品
 */
public class ShowsFavouriteAdd implements Serializable{
    String UserId;//加密收藏者userId
    String AuthorId;//加密作者id
    String ShowsId;//加密作品id
    int State;//状态 0 未收藏 1 已收藏

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(String authorId) {
        AuthorId = authorId;
    }

    public String getShowsId() {
        return ShowsId;
    }

    public void setShowsId(String showsId) {
        ShowsId = showsId;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }
}
