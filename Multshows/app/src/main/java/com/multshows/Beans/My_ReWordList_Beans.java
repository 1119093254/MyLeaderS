package com.multshows.Beans;

import java.io.Serializable;

/**
 * 打赏记录bean对象
 */
public class My_ReWordList_Beans implements Serializable{
    private int id;//id
    private String userName;//打赏人昵称
    private String userPic;//打赏人头像
    private String userNumber;//打赏人作品编号
    private String time;//打赏时间
    private String money;//打赏金额
    private boolean flag;//是否显示撤回

    public My_ReWordList_Beans(int id, String userName, String userPic, String userNumber, String time, String money, boolean flag) {
        this.id = id;
        this.userName = userName;
        this.userPic = userPic;
        this.userNumber = userNumber;
        this.time = time;
        this.money = money;
        this.flag = flag;
    }

    public My_ReWordList_Beans(int id, String userName, String userPic, String userNumber, String time, String money) {
        this.id = id;
        this.userName = userName;
        this.userPic = userPic;
        this.userNumber = userNumber;
        this.time = time;
        this.money = money;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
