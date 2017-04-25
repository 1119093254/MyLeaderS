package com.lxx.domain;
import java.io.Serializable;

/**
 * 描述：评论用户信息
 * 作者：李萧萧
 * 时间：2016.10.19
 */
public class ShowsCommentUser implements Serializable {
    String Id;//评论主键Id
    UserHead User;//评论者数据
    String Content;//评论内容
    int RecordTime;//记录时间(时间戳)
    String ReplyId;//记录时间(字符串)
    String RecordTimeStr;//父级评论Id
    String TargetUserId;//被回复者的userid
    String TargetUserNickName;//被回复者的昵称

    public ShowsCommentUser(String id, UserHead user, String content, String recordTimeStr,String targetUserNickName,String TargetUserId) {
        Id = id;
        User = user;
        Content = content;
        RecordTimeStr = recordTimeStr;
        TargetUserNickName=targetUserNickName;
        this.TargetUserId=TargetUserId;
    }

    public ShowsCommentUser() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public UserHead getUser() {
        return User;
    }

    public void setUser(UserHead user) {
        User = user;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int recordTime) {
        RecordTime = recordTime;
    }

    public String getReplyId() {
        return ReplyId;
    }

    public void setReplyId(String replyId) {
        ReplyId = replyId;
    }

    public String getRecordTimeStr() {
        return RecordTimeStr;
    }

    public void setRecordTimeStr(String recordTimeStr) {
        RecordTimeStr = recordTimeStr;
    }

    public String getTargetUserId() {
        return TargetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        TargetUserId = targetUserId;
    }

    public String getTargetUserNickName() {
        return TargetUserNickName;
    }

    public void setTargetUserNickName(String targetUserNickName) {
        TargetUserNickName = targetUserNickName;
    }

    @Override
    public String toString() {
        return "ShowsCommentUser{" +
                "Id='" + Id + '\'' +
                ", User=" + User +
                ", Content='" + Content + '\'' +
                ", RecordTime=" + RecordTime +
                ", ReplyId='" + ReplyId + '\'' +
                ", RecordTimeStr='" + RecordTimeStr + '\'' +
                ", TargetUserId='" + TargetUserId + '\'' +
                ", TargetUserNickName='" + TargetUserNickName + '\'' +
                '}';
    }
}
