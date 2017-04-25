package com.multshows.Beans;

import java.io.Serializable;

/**
 * 鲜花市场实体类
 */
public class UserAmount implements Serializable{
    private double Balance;//账户余额
    private int Flowers;//鲜花数量
    private double QuotaAmount;//可提现金额

    public UserAmount() {
    }

    public double getQuotaAmount() {
        return QuotaAmount;
    }

    public void setQuotaAmount(double quotaAmount) {
        QuotaAmount = quotaAmount;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public int getFlowers() {
        return Flowers;
    }

    public void setFlowers(int flowers) {
        Flowers = flowers;
    }
}
