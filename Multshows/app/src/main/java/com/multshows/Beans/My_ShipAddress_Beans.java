package com.multshows.Beans;

import java.io.Serializable;

/**
 * 我的收货地址bean
 */
public class My_ShipAddress_Beans implements Serializable{
    private int id;//收货地址id
    private String userName;//姓名
    private String userType;//用户类型
    private String userPhone;//手机号
    private String userAddress;//地址
    private boolean flag;//默认选择

    public My_ShipAddress_Beans(int id, String userName, String userType, String userPhone, String userAddress, boolean flag) {
        this.id = id;
        this.userName = userName;
        this.userType = userType;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
