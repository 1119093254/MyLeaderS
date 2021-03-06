package com.multshows.Beans;

import java.io.Serializable;

/**
 * 用户基本信息bean对象
 */
public class UserBase implements Serializable{
    private String UserId;//用户id
    private int Birthday;//生日
    private String BirthdayStr;//生日转化时间格式'yyyy-MM-dd HH:mm:ss'添加用户时可以不填写
    private int Sex;//性别
    private String NickName;//昵称
    private String RealName;//真实姓名
    private String Portrait;//头像图片
    private String Signature;//个性签名
    private int IsConfirm;//是否实名认证 1已认证 0未认证
    private int State;//状态1 启用 0  未启用
    private int UserType;//用户类型  0 妈妈 1宝宝
    private String ParentId;//家长主键
    private int Flowers;//鲜花数量
    private double Balance;//账户余额
    private int AbsenceTimes;//爽约次数
    private String PopularizeId;//推广id
    private String AccountId;//账号id--已加密，类型为宝宝时为0
    private String Relation;//跟家长的关系
    private String Mobile;//手机号：添加用户时可以不填写
    private int IsDefault;//是否默认
    public UserBase() {
    }

    public int getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int isDefault) {
        IsDefault = isDefault;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getBirthday() {
        return Birthday;
    }

    public void setBirthday(int birthday) {
        Birthday = birthday;
    }

    public String getBirthdayStr() {
        return BirthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        BirthdayStr = birthdayStr;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String portrait) {
        Portrait = portrait;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public int getIsConfirm() {
        return IsConfirm;
    }

    public void setIsConfirm(int isConfirm) {
        IsConfirm = isConfirm;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public int getFlowers() {
        return Flowers;
    }

    public void setFlowers(int flowers) {
        Flowers = flowers;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public int getAbsenceTimes() {
        return AbsenceTimes;
    }

    public void setAbsenceTimes(int absenceTimes) {
        AbsenceTimes = absenceTimes;
    }

    public String getPopularizeId() {
        return PopularizeId;
    }

    public void setPopularizeId(String popularizeId) {
        PopularizeId = popularizeId;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserBase{" +
                "UserId='" + UserId + '\'' +
                ", Birthday=" + Birthday +
                ", BirthdayStr='" + BirthdayStr + '\'' +
                ", Sex=" + Sex +
                ", NickName='" + NickName + '\'' +
                ", RealName='" + RealName + '\'' +
                ", Portrait='" + Portrait + '\'' +
                ", Signature='" + Signature + '\'' +
                ", IsConfirm=" + IsConfirm +
                ", State=" + State +
                ", UserType=" + UserType +
                ", ParentId='" + ParentId + '\'' +
                ", Flowers=" + Flowers +
                ", Balance=" + Balance +
                ", AbsenceTimes=" + AbsenceTimes +
                ", PopularizeId=" + PopularizeId +
                ", AccountId='" + AccountId + '\'' +
                ", Relation=" + Relation +
                ", Mobile='" + Mobile + '\'' +
                ", IsDefault=" + IsDefault +
                '}';
    }
}
