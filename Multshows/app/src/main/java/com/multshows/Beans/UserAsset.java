package com.multshows.Beans;

import java.io.Serializable;

/**
 * 资产实体类
 */
public class UserAsset implements Serializable{
    private double Balance;//余额
    private String UserId;//加密UserId
    private int Type;//资产变动类型   1支出2收入
    private String TypeName;//资产变动类型名称
    private int RecordTime;//记录日期
    private String Remark;//备注
    private int State;//到账类型
    private String StateName;//到账类型名称

    public UserAsset() {
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
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

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }
}
