package com.multshows.Beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：我的公益页 公益listBean
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class MyPublicWelfare_Beans {
    String Id;//公益id
    int month;//月
    int day;//日期
    List<String> mImageString;//公益图片地址集合
    String mContent;//公益内容
    List<UserHead> mApplause=new ArrayList<>();//给予掌声人名
    List<ShowsCommentUser> mCommentBeansList;//评论列表
    String muserpic;//用户头像
    String mUsername;//用户头像
    String mUserId;//用户id
    String time;//发表时间
    boolean flag=false;//评论布局是否显示
    private int mFlag;//0头部布局，1内容布局
    String MybackgroundImage;//我的背景图片地址
    String myImage;//我的头像地址
    public MyPublicWelfare_Beans(String Id,String UserId,String Username,String userpic,int month, int day, List<String> imageString, String content, List<UserHead> applause, List<ShowsCommentUser> commentBeansList,String time,String MybackgroundImage, String myImage,int flag) {
        this.Id = Id;
        this.month = month;
        this.day = day;
        mImageString = imageString;
        mContent = content;
        mApplause = applause;
        mCommentBeansList = commentBeansList;
        muserpic=userpic;
        mUserId=UserId;
        mUsername=Username;
        this.time=time;
        mFlag=flag;
        this.MybackgroundImage=MybackgroundImage;
        this.myImage=myImage;
    }



    public String getMybackgroundImage() {
        return MybackgroundImage;
    }

    public void setMybackgroundImage(String mybackgroundImage) {
        MybackgroundImage = mybackgroundImage;
    }

    public String getMyImage() {
        return myImage;
    }

    public void setMyImage(String myImage) {
        this.myImage = myImage;
    }

    public int getFlag() {
        return mFlag;
    }

    public void setFlag(int flag) {
        mFlag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMuserpic() {
        return muserpic;
    }

    public void setMuserpic(String muserpic) {
        this.muserpic = muserpic;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<String> getImageString() {
        return mImageString;
    }

    public void setImageString(List<String> imageString) {
        mImageString = imageString;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public List<UserHead> getApplause() {
        return mApplause;
    }

    public void setApplause(List<UserHead> applause) {
        mApplause = applause;
    }

    public List<ShowsCommentUser> getCommentBeansList() {
        return mCommentBeansList;
    }

    public void setCommentBeansList(List<ShowsCommentUser> commentBeansList) {
        mCommentBeansList = commentBeansList;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
