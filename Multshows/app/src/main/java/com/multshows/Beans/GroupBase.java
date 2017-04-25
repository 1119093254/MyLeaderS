package com.multshows.Beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/17.
 */
public class GroupBase implements Serializable{
    private String Id;//加密群id（创建群时不需要传值）
    private String RCGroupId;//融云提供 的群id
    private String GroupName;//群名称

    public GroupBase() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRCGroupId() {
        return RCGroupId;
    }

    public void setRCGroupId(String RCGroupId) {
        this.RCGroupId = RCGroupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }
}
