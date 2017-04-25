package com.lxx.Dao;

import java.io.Serializable;
/**
 * 描述：获取作品列表
 * 作者：李萧萧
 * 时间：2016.10.14
 */
public class ShowsTerm implements Serializable {
    String Title;//标题
    String Name;//用户昵称
    String ClassName;//分类名称
    int  IsFriend;//查询类型1好友作品 其他值按相应条件查询
    int  IsShow;//-1全部 0 不显示 1 显示
    String Number;//作品编号
    String userId;//加密userId
    String ShowsClassId;//加密分类userId
    int Type;//类型-1全部 1 作品 2闲置 3圈子
    String BeginTime;//发布开始时间
    String EndTime;//发布结束
    int IsOver;//是否售完 -1全部 0未售完 1已售完
    int PageIndex;//页数1开始
    int PageSize;//每页条数
    int SoType;//-1全部 0全部可见 1好友可见 2本人可见
    String LocalId;//当前登录用户的id
    int IsSelf;//是否是个人中心 0不是 1是
    int IsAll;//查询类型 1妈妈 2宝宝  3其它
    int IsTour;//是否游客 0不是  1是
    public ShowsTerm() {
    }

    public int getIsTour() {
        return IsTour;
    }

    public void setIsTour(int isTour) {
        IsTour = isTour;
    }

    public int getIsAll() {
        return IsAll;
    }

    public void setIsAll(int isAll) {
        IsAll = isAll;
    }

    public int getIsSelf() {
        return IsSelf;
    }

    public void setIsSelf(int isSelf) {
        IsSelf = isSelf;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLocalId() {
        return LocalId;
    }

    public void setLocalId(String localId) {
        LocalId = localId;
    }

    public int getSoType() {
        return SoType;
    }

    public void setSoType(int soType) {
        SoType = soType;
    }

    public String getTitile() {
        return Title;
    }

    public void setTitile(String titile) {
        Title = titile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getIsFriend() {
        return IsFriend;
    }

    public void setIsFriend(int isFriend) {
        IsFriend = isFriend;
    }

    public int getIsShow() {
        return IsShow;
    }

    public void setIsShow(int isShow) {
        IsShow = isShow;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userid) {
        this.userId = userid;
    }

    public String getShowsClassId() {
        return ShowsClassId;
    }

    public void setShowsClassId(String showsClassId) {
        ShowsClassId = showsClassId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getIsOver() {
        return IsOver;
    }

    public void setIsOver(int isOver) {
        IsOver = isOver;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }
}
