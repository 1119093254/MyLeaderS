package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：闲置列表bean
 * 作者：李萧萧
 * 时间：2016.9.28
 */
public class MyHome_Idle_Beans implements Serializable{
    String id;//闲置id
    String userid;//用户id
    String username;//用户昵称
    String userpic;//用户头像
    double money;//价格
    String data;//发表时间
    List<String> mStringList;//闲置图片地址
    String mTitle;//标题
    String content;//内容
    String address;//地点
    List<UserHead> likeUsername;//赞人姓名
    int messageNum;//留言数
    int Views;//浏览数量
    private int flag;//0头部布局，1内容布局
    int IsTrade=1;//是否在交易中 0不在（可删除）1在（不可删除）
    public MyHome_Idle_Beans(String id, String userid, String username, String userpic, int Views,double money, String data, List<String> stringList, String title, String content, String address,  List<UserHead> likeUsername, int messageNum, int flag) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.userpic = userpic;
        this.Views=Views;
        this.money = money;
        this.data = data;
        mStringList = stringList;
        mTitle = title;
        this.content = content;
        this.address = address;
        this.likeUsername = likeUsername;
        this.messageNum = messageNum;
        this.flag = flag;
    }

    public int getIsTrade() {
        return IsTrade;
    }

    public void setIsTrade(int isTrade) {
        IsTrade = isTrade;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getStringList() {
        return mStringList;
    }

    public void setStringList(List<String> stringList) {
        mStringList = stringList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<UserHead> getLikeUsername() {
        return likeUsername;
    }

    public void setLikeUsername(List<UserHead> likeUsername) {
        this.likeUsername = likeUsername;
    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
