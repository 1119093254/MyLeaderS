package com.multshows.Beans;

import java.io.Serializable;

/**
 * 切换用户信息
 */
public class Setting_LoginUser_Beans implements Serializable{
    private String userId;//加密id
    private String userNickName;//昵称
    private String userPic; //头像
    private String userName;//昵称
    private String phone;//手机号码
    private boolean flag;//登录状态
    private String user;//关系（妈妈/宝宝）mom/baby

    public Setting_LoginUser_Beans(String userId, String userNickName, String userPic, String userName, String phone, boolean flag, String user) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.userPic = userPic;
        this.userName = userName;
        this.phone = phone;
        this.flag = flag;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Setting_LoginUser_Beans{" +
                "userId='" + userId + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userPic='" + userPic + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", flag=" + flag +
                ", user='" + user + '\'' +
                '}';
    }
}
