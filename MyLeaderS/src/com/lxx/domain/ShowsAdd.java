package com.lxx.domain;



/**
 * 描述：添加作品/闲置/圈子实体类
 * 作者：李萧萧
 * 时间：2016.10.12
 */
public class ShowsAdd {
    String Id;//加密Id
    String MomId;//妈妈的id
    String UserId;//加密UserId(发布作品时传宝宝id)
    int ShowsType;//类型 1 作品 2闲置 3圈子
    String Title;//标题
    String Infos;//说明
    int  Amount;//库存数量
    int  Views;//浏览数量
    String ShowsClassId;//加密作品分类id
    int IsSale=0;//0不出售 1出售
    double Price;//售价
    double ExpressFee;//decimal	运费
    int IsShow;//0不显示 1显示
    int IsOver;//0未售完 1已售完
    String Address;//坐标地址
    String ShowsPhoto;//作品图片
    int SoType;//-1全部 0全部可见 1好友可见 2本人可见
    int IsVideo;//是否是视频  1 视频 2图片
    String VideoName;//视频名称
    int IsBabySee;// 0不可见，1可见
    public ShowsAdd() {
    }

    public int getIsBabySee() {
        return IsBabySee;
    }

    public void setIsBabySee(int isBabySee) {
        IsBabySee = isBabySee;
    }

    public String getMomId() {
        return MomId;
    }

    public void setMomId(String momId) {
        MomId = momId;
    }

    public int getIsVideo() {
        return IsVideo;
    }

    public void setIsVideo(int isVideo) {
        IsVideo = isVideo;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public int getSoType() {
        return SoType;
    }

    public void setSoType(int soType) {
        SoType = soType;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getShowsType() {
        return ShowsType;
    }

    public void setShowsType(int showsType) {
        ShowsType = showsType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInfos() {
        return Infos;
    }

    public void setInfos(String infos) {
        Infos = infos;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public String getShowsClassId() {
        return ShowsClassId;
    }

    public void setShowsClassId(String showsClassId) {
        ShowsClassId = showsClassId;
    }

    public int getIsSale() {
        return IsSale;
    }

    public void setIsSale(int isSale) {
        IsSale = isSale;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getExpressFee() {
        return ExpressFee;
    }

    public void setExpressFee(double expressFee) {
        ExpressFee = expressFee;
    }

    public int getIsShow() {
        return IsShow;
    }

    public void setIsShow(int isShow) {
        IsShow = isShow;
    }

    public int getIsOver() {
        return IsOver;
    }

    public void setIsOver(int isOver) {
        IsOver = isOver;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getShowsPhoto() {
        return ShowsPhoto;
    }

    public void setShowsPhoto(String showsPhoto) {
        ShowsPhoto = showsPhoto;
    }
}
