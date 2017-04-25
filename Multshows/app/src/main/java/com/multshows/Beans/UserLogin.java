package com.multshows.Beans;

import java.io.Serializable;

/**
 * 登录实体类
 *
 */
public class UserLogin implements Serializable{
    private String Account;//手机号
    private String Pwd;//密码
    private String AppKey;//第三方token
    private int Type;//0 普通登录 1 微信登录 2 微博登录 3 QQ登录

    public UserLogin() {

    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        AppKey = appKey;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
