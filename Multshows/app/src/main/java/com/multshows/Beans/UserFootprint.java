package com.multshows.Beans;

import java.io.Serializable;

/**
 * 我的足迹列表实体类
 */
public class UserFootprint implements Serializable{
    private String Id;//加密Id
    private User_ User;//查看人
    private User_ VisitedUser;//被查看人
    private int IsFriend;//好友申请状态:0 等待通过 1 通过申请 2 拒绝申请',3 未申请

    public UserFootprint() {
    }

    public int getIsFriend() {
        return IsFriend;
    }

    public void setIsFriend(int isFriend) {
        IsFriend = isFriend;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public User_ getUser() {
        return User;
    }

    public void setUser(User_ user) {
        User = user;
    }

    public User_ getVisitedUser() {
        return VisitedUser;
    }

    public void setVisitedUser(User_ visitedUser) {
        VisitedUser = visitedUser;
    }
}
