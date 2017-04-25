package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 好友信息beans对象
 */
public class Friends_friend_Beans implements Serializable{
    private String Id;//加密Id
    private String UserId;//加密UserId
    private String TargetUserId;//目标用户加密userId
    private String nickName;  //用户昵称
    private String userPic;  //用户头像
    private List<UserTag> UserTagList;//用户兴趣标签
    private int State;//好友申请状态  0 等待通过 1 通过申请 2 拒绝申请
    private String sortLetters;  //显示数据拼音的首字母
    private boolean check;  //显示数据拼音的首字母
    private int flag;//0头部布局，1内容布局

    public Friends_friend_Beans(String id, String userId, String targetUserId, String nickName, String userPic,
                                List<UserTag> UserTagList,int state, String sortLetters, int flag) {
        Id = id;
        UserId = userId;
        TargetUserId = targetUserId;
        this.nickName = nickName;
        this.userPic = userPic;
        this.UserTagList=UserTagList;
        State = state;
        this.sortLetters = sortLetters;
        this.flag = flag;
    }

    public Friends_friend_Beans(String id, String userId, String targetUserId, String nickName,
                                String userPic, List<UserTag> userTagList, int state, String sortLetters, boolean check, int flag) {
        Id = id;
        UserId = userId;
        TargetUserId = targetUserId;
        this.nickName = nickName;
        this.userPic = userPic;
        UserTagList = userTagList;
        State = state;
        this.sortLetters = sortLetters;
        this.check = check;
        this.flag = flag;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public List<UserTag> getUserTagList() {
        return UserTagList;
    }

    public void setUserTagList(List<UserTag> userTagList) {
        UserTagList = userTagList;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTargetUserId() {
        return TargetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        TargetUserId = targetUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Friends_friend_Beans{" +
                "Id='" + Id + '\'' +
                ", UserId='" + UserId + '\'' +
                ", TargetUserId='" + TargetUserId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userPic='" + userPic + '\'' +
                ", UserTagList=" + UserTagList +
                ", State=" + State +
                ", sortLetters='" + sortLetters + '\'' +
                ", check=" + check +
                ", flag=" + flag +
                '}';
    }
}
