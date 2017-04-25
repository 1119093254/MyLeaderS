package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：获取作品列表
 * 作者：李萧萧
 * 时间：2016.10.14
 */
public class Shows implements Serializable {
    String Id;//加密Id
    int ShowsType;//类型 1 作品 2闲置 3圈子
    String ShowsTypeName;//类型名称
    String Title;//标题
    String Infos;//说明
    int Amount;//库存数量
    int Views;//浏览数量
    String SerialNumber;//作品编号
    int IsSale;//0不出售 1出售
    double Price;//售价
    double ExpressFee;//运费
    int IsOver;////0未售完 1已售完
    ShowsClassBase ShowsClass;//分类信息
    UserHead User;//宝宝信息
    UserHead MomUser;//妈妈信息
    String Address;//坐标地址
    String ShowsPhoto;	//作品图片
    int LikeCount;//总点赞数量
    List<UserHead> LikeUser;//点赞用户信息
    int CommentCount;//总评论数量
    List<ShowsCommentUser> CommentUser;//评论用户信息
    List<UserHead> FlowerUser;//送花者信息
    int FloweCount;//总送花数量
    List<UserHead> RewardUser;//打赏者信息
    int RewardCount;//总打赏数量
    List<UserHead> FavUser;//收藏者信息
    int FavCount;//总收藏者数量
    int PublishTime;//发布时间戳
    String PublishTimeStr;//发布时间
    int IsVideo;//是否是视频  1 视频 2图片
    String VideoName; //视频名称
    int SoType=0;//0全部可见1好友可见2本人可见
    int IsTrade=1;//是否在交易中 0不在（可删除）1在（不可删除）
    public int getIsVideo() {
        return IsVideo;
    }

    public int getSoType() {
        return SoType;
    }

    public void setSoType(int soType) {
        SoType = soType;
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

    public int getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(int publishTime) {
        PublishTime = publishTime;
    }

    public List<ShowsCommentUser> getCommentUser() {
        return CommentUser;
    }

    public void setCommentUser(List<ShowsCommentUser> commentUser) {
        CommentUser = commentUser;
    }

    public String getPublishTimeStr() {
        return PublishTimeStr;
    }

    public void setPublishTimeStr(String PublishTimeStr) {
        PublishTimeStr = PublishTimeStr;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getShowsType() {
        return ShowsType;
    }

    public void setShowsType(int showsType) {
        ShowsType = showsType;
    }

    public String getShowsTypeName() {
        return ShowsTypeName;
    }

    public void setShowsTypeName(String showsTypeName) {
        ShowsTypeName = showsTypeName;
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

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
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

    public int getIsOver() {
        return IsOver;
    }

    public void setIsOver(int isOver) {
        IsOver = isOver;
    }

    public ShowsClassBase getShowsClass() {
        return ShowsClass;
    }

    public void setShowsClass(ShowsClassBase showsClass) {
        ShowsClass = showsClass;
    }

    public UserHead getUser() {
        return User;
    }

    public void setUser(UserHead user) {
        User = user;
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

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int likeCount) {
        LikeCount = likeCount;
    }

    public List<UserHead> getLikeUser() {
        return LikeUser;
    }

    public void setLikeUser(List<UserHead> likeUser) {
        LikeUser = likeUser;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public List<UserHead> getFlowerUser() {
        return FlowerUser;
    }

    public void setFlowerUser(List<UserHead> flowerUser) {
        FlowerUser = flowerUser;
    }

    public int getFloweCount() {
        return FloweCount;
    }

    public void setFloweCount(int floweCount) {
        FloweCount = floweCount;
    }

    public List<UserHead> getRewardUse() {
        return RewardUser;
    }

    public void setRewardUse(List<UserHead> rewardUse) {
        RewardUser = rewardUse;
    }

    public int getRewardCount() {
        return RewardCount;
    }

    public void setRewardCount(int rewardCount) {
        RewardCount = rewardCount;
    }

    public List<UserHead> getRewardUser() {
        return RewardUser;
    }

    public void setRewardUser(List<UserHead> rewardUser) {
        RewardUser = rewardUser;
    }

    public List<UserHead> getFavUser() {
        return FavUser;
    }

    public void setFavUser(List<UserHead> favUser) {
        FavUser = favUser;
    }

    public int getFavCount() {
        return FavCount;
    }

    public void setFavCount(int favCount) {
        FavCount = favCount;
    }

    public UserHead getMomUser() {
        return MomUser;
    }

    public void setMomUser(UserHead momUser) {
        MomUser = momUser;
    }

    public int getIsTrade() {
        return IsTrade;
    }

    public void setIsTrade(int isTrade) {
        IsTrade = isTrade;
    }
}
