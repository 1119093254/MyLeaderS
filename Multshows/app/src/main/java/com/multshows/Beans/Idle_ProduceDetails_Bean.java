package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;
/**
 * 描述：宝贝详情bean
 * 作者：李萧萧
 * 时间：2016.10.7
 */
public class Idle_ProduceDetails_Bean implements Serializable{

    String userid;
    String name;//昵称
    String mTime;//发表时间
    String mUserpic;//用户头像
    String content;//正文内容
    double price;//价格
    String mLocation;//位置
    List<String> mPicString;//图片详情
    List<UserHead> mPicLikeString;//赞人头像
    int num;//浏览人数量
    String userbaseId;//发表者id
    List<Idle_ProduceComment_Beans> workComment_beanes;//评论内容列表
    String mTitle;
    String Idlenum;//库存数量
    int Type;//类型 1 作品 2闲置 3圈子
    public Idle_ProduceDetails_Bean(String userId,String name, String mTime, String mUserpic,String mTitle, String content, double price, String mLocation, List<String> mPicString, List<UserHead> mPicLikeString, int num, List<Idle_ProduceComment_Beans> workComment_beanes,String userbaseId,String Idlenum,int mType) {
        this.name = name;
        this.Idlenum=Idlenum;
        this.userid=userId;
        this.mTime = mTime;
        this.mTitle=mTitle;
        this.mUserpic = mUserpic;
        this.content = content;
        this.price = price;
        this.mLocation = mLocation;
        this.mPicString = mPicString;
        this.mPicLikeString = mPicLikeString;
        this.num = num;
        this.workComment_beanes = workComment_beanes;
        this.userbaseId=userbaseId;
        Type=mType;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getIdlenum() {
        return Idlenum;
    }

    public void setIdlenum(String idlenum) {
        Idlenum = idlenum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getUserpic() {
        return mUserpic;
    }

    public void setUserpic(String userpic) {
        mUserpic = userpic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public List<String> getPicString() {
        return mPicString;
    }

    public void setPicString(List<String> picString) {
        mPicString = picString;
    }

    public List<UserHead> getPicLikeString() {
        return mPicLikeString;
    }

    public void setPicLikeString(List<UserHead> picLikeString) {
        mPicLikeString = picLikeString;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUserbaseId() {
        return userbaseId;
    }

    public void setUserbaseId(String userbaseId) {
        this.userbaseId = userbaseId;
    }

    public List<Idle_ProduceComment_Beans> getWorkComment_beanes() {
        return workComment_beanes;
    }

    public void setWorkComment_beanes(List<Idle_ProduceComment_Beans> workComment_beanes) {
        this.workComment_beanes = workComment_beanes;
    }
}
