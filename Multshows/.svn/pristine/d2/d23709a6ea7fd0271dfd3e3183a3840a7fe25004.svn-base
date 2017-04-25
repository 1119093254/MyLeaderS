package com.multshows.Beans;

import java.io.Serializable;

/**
 * 获取交易列表 实体类
 */
public class PayRecord implements Serializable{
    private String Id;//交易记录Id
    private int Type;//类型 1售出商品，2，购买商品，3打赏，4，收到打赏，5购买鲜花，6，发布任务，7，完成任务，8充值，9提现成功，10提现失败
    private String TypeName;//类型名称
    private double Amount;//金额/数量
    private int RecordTime;//记录时间
    private UserBase User;//用户
    private int InType;//收入支出类型：1收入2支出
    private String Remark;//备注
    private double TransFee;//平台手续费
    private String TradeId;//交易单id (注：此字段只有提现银行卡失败或者成功才会有返回值以便用于再次申请提现)
    private double ExpressFee;//运费
    private int PayType;//交易类型：1.余额 2一麻袋 3银联 4微信 5 IOS支付-
    private String OtherNickName;//对方账户昵称
    private String CashId;//提现id
    private int ExtendLevel;//推广级别
    private double ExtendRate;//推广利率
    private int ExtendRewardType;//推广奖励方式：1 打赏 2 订单
    public PayRecord() {
    }

    public int getExtendLevel() {
        return ExtendLevel;
    }

    public void setExtendLevel(int extendLevel) {
        ExtendLevel = extendLevel;
    }

    public double getExtendRate() {
        return ExtendRate;
    }

    public void setExtendRate(double extendRate) {
        ExtendRate = extendRate;
    }

    public int getExtendRewardType() {
        return ExtendRewardType;
    }

    public void setExtendRewardType(int extendRewardType) {
        ExtendRewardType = extendRewardType;
    }

    public String getCashId() {
        return CashId;
    }

    public void setCashId(String cashId) {
        CashId = cashId;
    }

    public String getOtherNickName() {
        return OtherNickName;
    }

    public void setOtherNickName(String otherNickName) {
        OtherNickName = otherNickName;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int payType) {
        PayType = payType;
    }

    public double getExpressFee() {
        return ExpressFee;
    }

    public void setExpressFee(double expressFee) {
        ExpressFee = expressFee;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public double getTransFee() {
        return TransFee;
    }

    public void setTransFee(double transFee) {
        TransFee = transFee;
    }

    public String getTradeId() {
        return TradeId;
    }

    public void setTradeId(String tradeId) {
        TradeId = tradeId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }

    public UserBase getUser() {
        return User;
    }

    public void setUser(UserBase user) {
        User = user;
    }

    public int getInType() {
        return InType;
    }

    public void setInType(int inType) {
        InType = inType;
    }
}
