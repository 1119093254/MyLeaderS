package com.multshows.Beans;

import java.io.Serializable;

/**
 * 群成员信息
 */
public class GroupMember implements Serializable{
    private String GroupId;//加密群id
    private String UserId;//加密用户id
    private String Alias;//在本群的昵称
    private GroupUser UserHead;//用户头像

    private int flag;//buju

    public GroupMember() {
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public GroupUser getUserHead() {
        return UserHead;
    }

    public void setUserHead(GroupUser userHead) {
        UserHead = userHead;
    }
}
