package com.multshows.Beans;

import java.io.Serializable;

/**
 * 打赏记录实体类
 */
public class ShowsReward implements Serializable{
    private String Id;//加密主键id
    private UserBase User;//用户信息
    private UserBase Author;//作者信息
    private ShowsBase Shows;//作品信息
    private double Amount;//打赏金额
    private int RewardTime ;//打赏时间
    private int RecallTime ;//撤回时间
    private String RewardTimeStr ;//打赏时间时间格式字符串
    private String RecallTimeStr ;//撤回时间时间格式字符串
    private int State;//0未支付 1 已打赏 2 已撤回 3 已完成
    private String PaymentNumber;//支付编号
    private int IsRecall;//0 不可撤回 1可撤回
    private double TransFee;//平台手续费

    public ShowsReward(String id, UserBase user, UserBase author, ShowsBase shows, double amount,
                       int rewardTime, int recallTime, int state, String paymentNumber) {
        Id = id;
        User = user;
        Author = author;
        Shows = shows;
        Amount = amount;
        RewardTime = rewardTime;
        RecallTime = recallTime;
        State = state;
        PaymentNumber = paymentNumber;
    }

    public ShowsReward() {
    }

    public double getTransFee() {
        return TransFee;
    }

    public void setTransFee(double transFee) {
        TransFee = transFee;
    }

    public int getIsRecall() {
        return IsRecall;
    }

    public void setIsRecall(int isRecall) {
        IsRecall = isRecall;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public UserBase getUser() {
        return User;
    }

    public void setUser(UserBase user) {
        User = user;
    }

    public UserBase getAuthor() {
        return Author;
    }

    public void setAuthor(UserBase author) {
        Author = author;
    }

    public ShowsBase getShows() {
        return Shows;
    }

    public void setShows(ShowsBase shows) {
        Shows = shows;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getRewardTime() {
        return RewardTime;
    }

    public void setRewardTime(int rewardTime) {
        RewardTime = rewardTime;
    }

    public int getRecallTime() {
        return RecallTime;
    }

    public void setRecallTime(int recallTime) {
        RecallTime = recallTime;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getPaymentNumber() {
        return PaymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        PaymentNumber = paymentNumber;
    }

    public String getRewardTimeStr() {
        return RewardTimeStr;
    }

    public void setRewardTimeStr(String rewardTimeStr) {
        RewardTimeStr = rewardTimeStr;
    }

    public String getRecallTimeStr() {
        return RecallTimeStr;
    }

    public void setRecallTimeStr(String recallTimeStr) {
        RecallTimeStr = recallTimeStr;
    }
}
