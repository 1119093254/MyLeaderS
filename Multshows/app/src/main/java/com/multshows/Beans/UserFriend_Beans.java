package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 好友列表 好友bean对象
 */
public class UserFriend_Beans implements Serializable{
    private String Id;//加密Id
    //private User_ User;//加密UserId
    private User_ TargetUser;//目标用户加密userId
    private int State;//好友申请状态  0 等待通过 1 通过申请 2 拒绝申请
    private int IsSelf;//是否是当前用户 0不是 1是
   /* private String NickName;//昵称
    private String Portrait;//头像图片*/
    private List<UserTag> UserTagList;//用户兴趣标签
    private String NameRemark;//备注

    public UserFriend_Beans() {
    }

    public String getNameRemark() {
        return NameRemark;
    }

    public void setNameRemark(String nameRemark) {
        NameRemark = nameRemark;
    }

    public int getIsSelf() {
        return IsSelf;
    }

    public void setIsSelf(int isSelf) {
        IsSelf = isSelf;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public User_ getTargetUser() {
        return TargetUser;
    }

    public void setTargetUser(User_ targetUser) {
        TargetUser = targetUser;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public List<UserTag> getUserTagList() {
        return UserTagList;
    }

    public void setUserTagList(List<UserTag> userTagList) {
        UserTagList = userTagList;
    }
}
