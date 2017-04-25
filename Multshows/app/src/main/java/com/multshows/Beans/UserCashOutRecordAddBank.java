package com.multshows.Beans;

import java.io.Serializable;

/**
 * 提现至银行卡实体类
 */
public class UserCashOutRecordAddBank implements Serializable{
    private String UserId;//用户Id
    private double TradeCash;//提现金额
    private String BankId;//银行卡Id

    public UserCashOutRecordAddBank() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public double getTradeCash() {
        return TradeCash;
    }

    public void setTradeCash(double tradeCash) {
        TradeCash = tradeCash;
    }

    public String getBankId() {
        return BankId;
    }

    public void setBankId(String bankId) {
        BankId = bankId;
    }
}
