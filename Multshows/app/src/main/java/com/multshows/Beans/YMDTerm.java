package com.multshows.Beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/22.
 */
public class YMDTerm implements Serializable{
    private String userId;//用户加密id
    private String accountName;//一麻袋账号
    private String realName;//真实姓名
    private String cardNo;//身份证

    public YMDTerm() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
