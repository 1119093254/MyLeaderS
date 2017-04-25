package com.multshows.Beans;

import java.io.Serializable;

/**
 * 提现信息实体
 */
public class UserCashOutRecord implements Serializable{
    private String TradeId;//交易单ID
    private UserHead User;//用户信息
    private String SeCashInfox;//提现信息
    private double TradeCash;//提现金额
    private double TradeFee;//交易手续费
    private double Balance;//当前余额
    private int VerifyStatus;//审核状态：-1,审核失败 0,未审核 1,待审核  2,审核通过
    private int CashStatus;//提现状态：-1,提现失败 0,等待提现 1,提现已完成
    private String Remark;//备注
    private int VerifyTime;//审核时间(时间戳)
    private String VerifyTimeStr;//审核时间(字符串)
    private int RecordTime;//交易时间(时间戳)
    private String RecordTimeStr;//交易时间(字符串)
    private int TradeType;//提现类型：1提现一麻袋2提现银行卡
    private UserBankBase UserBank;//用户银行卡信息
    private String UserYmdNumber;//用户一麻袋账号
    private String ErrorMsg;//提现失败信息

    public UserCashOutRecord() {
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public String getTradeId() {
        return TradeId;
    }

    public void setTradeId(String tradeId) {
        TradeId = tradeId;
    }

    public UserHead getUser() {
        return User;
    }

    public void setUser(UserHead user) {
        User = user;
    }

    public String getSeCashInfox() {
        return SeCashInfox;
    }

    public void setSeCashInfox(String seCashInfox) {
        SeCashInfox = seCashInfox;
    }

    public double getTradeCash() {
        return TradeCash;
    }

    public void setTradeCash(double tradeCash) {
        TradeCash = tradeCash;
    }

    public double getTradeFee() {
        return TradeFee;
    }

    public void setTradeFee(double tradeFee) {
        TradeFee = tradeFee;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public int getVerifyStatus() {
        return VerifyStatus;
    }

    public void setVerifyStatus(int verifyStatus) {
        VerifyStatus = verifyStatus;
    }

    public int getCashStatus() {
        return CashStatus;
    }

    public void setCashStatus(int cashStatus) {
        CashStatus = cashStatus;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getVerifyTime() {
        return VerifyTime;
    }

    public void setVerifyTime(int verifyTime) {
        VerifyTime = verifyTime;
    }

    public String getVerifyTimeStr() {
        return VerifyTimeStr;
    }

    public void setVerifyTimeStr(String verifyTimeStr) {
        VerifyTimeStr = verifyTimeStr;
    }

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }

    public String getRecordTimeStr() {
        return RecordTimeStr;
    }

    public void setRecordTimeStr(String recordTimeStr) {
        RecordTimeStr = recordTimeStr;
    }

    public int getTradeType() {
        return TradeType;
    }

    public void setTradeType(int tradeType) {
        TradeType = tradeType;
    }

    public UserBankBase getUserBank() {
        return UserBank;
    }

    public void setUserBank(UserBankBase userBank) {
        UserBank = userBank;
    }

    public String getUserYmdNumber() {
        return UserYmdNumber;
    }

    public void setUserYmdNumber(String userYmdNumber) {
        UserYmdNumber = userYmdNumber;
    }
}
