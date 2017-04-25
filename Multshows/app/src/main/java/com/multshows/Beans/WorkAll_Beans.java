package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;
/**
 * 描述：所有作品列表bean
 * 作者：李萧萧
 * 时间：2016.9.22
 */
public class WorkAll_Beans implements Serializable{
    String id;//加密Id
    String username;//用户姓名
    String  userid;//用户id
    String userPic;//用户头像
    String date;//发布时间
    String workId;//作品编号
    String mThemetitle;//作品主题标题
    String mContent;//作品内容
    int UserType;// 0妈妈 1宝宝
    List<String> imageStrinig;//图片地址集合
    List<UserHead> mRewardimageUrl;//打赏人的头像地址集合
    List<UserHead> likeUsername;//赞人姓名
    List<UserHead> flowerUsername;//送花人姓名
    List<UserHead> favUser;//收藏者信息
    List<ShowsCommentUser> mCommentBeansList;//评论bean
    boolean flag=false;//评论布局是否显示
    int IsVideo=2;//是否是视频  1 视频 2图片
    String VideoName="";//视频名称
    String price;//作品价格
    int type=2;//1 圈子 2，作品
    int Views;//浏览数量
    int SoType=0;//0全部可见1好友可见2本人可见
    private int visiable;//0头部布局，1内容布局
    int IsTrade=1;//是否在交易中 0不在（可删除）1在（不可删除）

    public int getIsTrade() {
        return IsTrade;
    }

    public void setIsTrade(int isTrade) {
        IsTrade = isTrade;
    }

    public int getType() {
        return type;
    }

    public int getSoType() {
        return SoType;
    }

    public void setSoType(int soType) {
        SoType = soType;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getThemetitle() {
        return mThemetitle;
    }

    public void setThemetitle(String themetitle) {
        mThemetitle = themetitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public List<String> getImageStrinig() {
        return imageStrinig;
    }

    public void setImageStrinig(List<String> imageStrinig) {
        this.imageStrinig = imageStrinig;
    }

    public List<UserHead> getLikeUsername() {
        return likeUsername;
    }

    public void setLikeUsername(List<UserHead> likeUsername) {
        this.likeUsername = likeUsername;
    }

    public List<UserHead> getFlowerUsername() {
        return flowerUsername;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public void setFlowerUsername(List<UserHead> flowerUsername) {
        this.flowerUsername = flowerUsername;
    }

    public List<ShowsCommentUser> getCommentBeansList() {
        return mCommentBeansList;
    }

    public void setCommentBeansList(List<ShowsCommentUser> commentBeansList) {
        mCommentBeansList = commentBeansList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UserHead> getRewardimageUrl() {
        return mRewardimageUrl;
    }

    public void setRewardimageUrl(List<UserHead> rewardimageUrl) {
        mRewardimageUrl = rewardimageUrl;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getVisiable() {
        return visiable;
    }

    public void setVisiable(int visiable) {
        this.visiable = visiable;
    }

    public WorkAll_Beans(String id, String username, int type,String price, String userid, String userPic,int Views,String date, String workId, String themetitle, String content, List<String> imageStrinig, List<UserHead> rewardimageUrl, List<UserHead> likeUsername, List<UserHead> flowerUsername, List<ShowsCommentUser> commentBeansList, List<UserHead> favUser,int mVisiable) {
        this.id=id;
        this.Views=Views;
        this.price=price;
        this.UserType=type;
        this.username = username;
        this.userid = userid;
        this.userPic = userPic;
        this.date = date;
        this.workId = workId;
        mThemetitle = themetitle;
        mContent = content;
        this.imageStrinig = imageStrinig;
        mRewardimageUrl = rewardimageUrl;
        this.likeUsername = likeUsername;
        this.flowerUsername = flowerUsername;
        mCommentBeansList = commentBeansList;
        this.favUser=favUser;
        visiable=mVisiable;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<UserHead> getFavUser() {
        return favUser;
    }

    public void setFavUser(List<UserHead> favUser) {
        this.favUser = favUser;
    }


    @Override
    public String toString() {
        return "WorkAll_Beans{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", userPic='" + userPic + '\'' +
                ", date='" + date + '\'' +
                ", workId='" + workId + '\'' +
                ", mThemetitle='" + mThemetitle + '\'' +
                ", mContent='" + mContent + '\'' +
                ", imageStrinig=" + imageStrinig +
                ", mRewardimageUrl=" + mRewardimageUrl +
                ", likeUsername=" + likeUsername +
                ", flowerUsername=" + flowerUsername +
                ", mCommentBeansList=" + mCommentBeansList +
                ", flag=" + flag +
                ", IsVideo=" + IsVideo +
                ", VideoName='" + VideoName + '\'' +
                '}';
    }
}
