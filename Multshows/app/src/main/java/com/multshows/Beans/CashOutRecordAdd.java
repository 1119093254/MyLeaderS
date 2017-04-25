package com.multshows.Beans;

import java.io.Serializable;

/**
 * 提现至一麻袋实体类
 */
public class CashOutRecordAdd implements Serializable{
    private String UserId;//用户Id
    private double TradeCash;//提现金额
    private String YmdNumber;//e麻袋数字证书

    public CashOutRecordAdd() {
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

    public String getYmdNumber() {
        return YmdNumber;
    }

    public void setYmdNumber(String ymdNumber) {
        YmdNumber = ymdNumber;
    }
}
