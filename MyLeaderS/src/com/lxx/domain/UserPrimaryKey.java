package com.lxx.domain;


import java.io.Serializable;

/**
 * 登录返回实体类
 */
public class UserPrimaryKey implements Serializable{
	 private String UserId;//加密userId
	    private String AccountId;//加密accountId
	    private String token;//融云token值
	    private int IsBind;//是否绑定第三方账号 0未绑定 1已绑定
	    private String IsMobile;//手机号
	    int Type;//1贵宾 2家长 3宝宝

    public UserPrimaryKey() {
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getIsMobile() {
        return IsMobile;
    }

    public void setIsMobile(String isMobile) {
        IsMobile = isMobile;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIsBind() {
        return IsBind;
    }

    public void setIsBind(int isBind) {
        IsBind = isBind;
    }
}
