package com.multshows.Beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 账户实体对象
 */
public class Account implements Serializable{
    private String AccountId;//加密后的字符
    private String UserId;//用户表id
    private String LoginName;//登录名
    private String Mobile;//手机号
    private String LoginPassword;//登录密码
    private String TogglePassword;//切换密码
    private Date RegisterTime;//注册时间
    private String Weibo;//微博账号
    private String QQ;//Qq账号
    private  int IsConfirm;//默认0，未实名
    private int Birthday;//生日
    private  int Sex;//1男 2女
    private String NickName;//昵称
    private String RealName;//真实姓名
    private String Id;//对应用户加密后主键
    private String MomRealName;//真实姓名
    private String State;//1启用 0 未启用
    private String Portrait;//头像图片
    private  int UserType;//0妈妈 1宝宝
    private String ParentId;//如果是宝宝数据此字段对应妈妈加密后主键
    private  int Relation;//关系
    private String Signature;//个性签名
    private  int Flowers;//鲜花数量
    private double Balance;//账户余额
    private  int PopularizeId;//推广id
    private String Tags;//个性标签 逗号隔开
    private  int AbsenceTimes;//爽约次数
    private String PhotoName;//图片名称
    private  int PhotoType;//图片类型
    private  int LoginType;//登录类型
    private  int PwdType;//1登录密码 2 切换密码
    private String NewPwd;//新密码
    private List<UserTag> ListUserTag;

    public Account() {}

    public List<UserTag> getListUserTag() {
        return ListUserTag;
    }

    public void setListUserTag(List<UserTag> listUserTag) {
        ListUserTag = listUserTag;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getLoginPassword() {
        return LoginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        LoginPassword = loginPassword;
    }

    public String getTogglePassword() {
        return TogglePassword;
    }

    public void setTogglePassword(String togglePassword) {
        TogglePassword = togglePassword;
    }

    public Date getRegisterTime() {
        return RegisterTime;
    }

    public void setRegisterTime(Date registerTime) {
        RegisterTime = registerTime;
    }

    public String getWeibo() {
        return Weibo;
    }

    public void setWeibo(String weibo) {
        Weibo = weibo;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public int getIsConfirm() {
        return IsConfirm;
    }

    public void setIsConfirm(int isConfirm) {
        IsConfirm = isConfirm;
    }

    public int getBirthday() {
        return Birthday;
    }

    public void setBirthday(int birthday) {
        Birthday = birthday;
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMomRealName() {
        return MomRealName;
    }

    public void setMomRealName(String momRealName) {
        MomRealName = momRealName;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String portrait) {
        Portrait = portrait;
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

    public int getRelation() {
        return Relation;
    }

    public void setRelation(int relation) {
        Relation = relation;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
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

    public int getPopularizeId() {
        return PopularizeId;
    }

    public void setPopularizeId(int popularizeId) {
        PopularizeId = popularizeId;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public int getAbsenceTimes() {
        return AbsenceTimes;
    }

    public void setAbsenceTimes(int absenceTimes) {
        AbsenceTimes = absenceTimes;
    }

    public String getPhotoName() {
        return PhotoName;
    }

    public void setPhotoName(String photoName) {
        PhotoName = photoName;
    }

    public int getPhotoType() {
        return PhotoType;
    }

    public void setPhotoType(int photoType) {
        PhotoType = photoType;
    }

    public int getLoginType() {
        return LoginType;
    }

    public void setLoginType(int loginType) {
        LoginType = loginType;
    }

    public int getPwdType() {
        return PwdType;
    }

    public void setPwdType(int pwdType) {
        PwdType = pwdType;
    }

    public String getNewPwd() {
        return NewPwd;
    }

    public void setNewPwd(String newPwd) {
        NewPwd = newPwd;
    }
}
