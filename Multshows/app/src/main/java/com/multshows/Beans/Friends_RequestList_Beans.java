package com.multshows.Beans;

import java.io.Serializable;

/**
 * 好友申请列表  数据beans对象
 */
public class Friends_RequestList_Beans implements Serializable{
    private String userId; //用户id
    private String nickName;  //用户昵称
    private String userPic;  //用户头像
    private int flag;//是否同意添加0 已同意，1未处理

    public Friends_RequestList_Beans(String userId, String nickName, String userPic,int flag) {
        this.userId = userId;
        this.nickName = nickName;
        this.userPic = userPic;
        this.flag=flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
