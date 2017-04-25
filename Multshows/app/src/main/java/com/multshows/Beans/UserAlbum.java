package com.multshows.Beans;

/**
 * Created by Administrator on 2016/10/18.
 */
public class UserAlbum {
    String Id;//加密id
    String UserId;//加密UserId
    String UserType;//用户类型 1妈妈2宝宝
    String PhotoName;//图片名称,逗号隔开
    String IPhotoTyped;//图片类型 1上传图片2作品同步
    int SoType;//全部可见1好友可见2本地可见
    String Remark;//备注说明
    public UserAlbum() {
    }

    public int getSoType() {
        return SoType;
    }

    public void setSoType(int soType) {
        SoType = soType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
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

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getPhotoName() {
        return PhotoName;
    }

    public void setPhotoName(String photoName) {
        PhotoName = photoName;
    }

    public String getIPhotoTyped() {
        return IPhotoTyped;
    }

    public void setIPhotoTyped(String IPhotoTyped) {
        this.IPhotoTyped = IPhotoTyped;
    }

}
