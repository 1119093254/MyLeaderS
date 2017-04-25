package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public class User  implements Serializable{
    String UserId;//加密用户Id
    int Birthday;//生日
    int Sex;//性别1男 2女
    String NickName;//昵称
    String RealName;//真实姓名
    String Portrait;//头像图片
    String Signature;//个性签名
    int IsConfirm;//是否实名认证 1已认证 0未认证
    int State;//状态1 启用 0  未启用
    int UserType;//用户类型  0 妈妈 1宝宝
    String ParentId;//家长主键
    int Flowers;//鲜花数量
    double Balance;//账户余额
    int AbsenceTimes;//爽约次数
    String Mobile="";//手机号
    String PopularizeId;//推广id
    String AccountId;//账号id--已加密
    String LoginName;//登录名
    String LoginPassword;//登录密码
    String TogglePassword;//切换密码
    String Weibo;//微博
    String QQ;//QQ
    String SeenMeCount;//看过我的数量
    int ReadMyCount;//我看过的数量
    double RewardAmount;//打赏金额
    List<UserTag> ListUserTag;//用户兴趣标签集合
    int IsFriend;//好友申请状态 0等待通过，1通过申请2 拒绝申请3未申请
    private String PayPassword;//支付密码
    private String YmdAccount;//一麻袋账号
    private String YmdName;//一麻袋姓名
    private String YmdNumber;//一麻袋数字证书

    public User() {
    }

    public String getPayPassword() {
        return PayPassword;
    }

    public void setPayPassword(String payPassword) {
        PayPassword = payPassword;
    }


    public String getYmdAccount() {
        return YmdAccount;
    }

    public void setYmdAccount(String ymdAccount) {
        YmdAccount = ymdAccount;
    }

    public String getYmdName() {
        return YmdName;
    }

    public void setYmdName(String ymdName) {
        YmdName = ymdName;
    }

    public String getYmdNumber() {
        return YmdNumber;
    }

    public void setYmdNumber(String ymdNumber) {
        YmdNumber = ymdNumber;
    }

    public int getIsFriend() {
        return IsFriend;
    }

    public void setIsFriend(int isFriend) {
        IsFriend = isFriend;
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

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
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

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
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

    public String getSeenMeCount() {
        return SeenMeCount;
    }

    public void setSeenMeCount(String seenMeCount) {
        SeenMeCount = seenMeCount;
    }

    public int getReadMyCount() {
        return ReadMyCount;
    }

    public void setReadMyCount(int readMyCount) {
        ReadMyCount = readMyCount;
    }

    public double getRewardAmount() {
        return RewardAmount;
    }

    public void setRewardAmount(double rewardAmount) {
        RewardAmount = rewardAmount;
    }

    public List<UserTag> getListUserTag() {
        return ListUserTag;
    }

    public void setListUserTag(List<UserTag> listUserTag) {
        ListUserTag = listUserTag;
    }
}