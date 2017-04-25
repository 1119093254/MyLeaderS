package com.multshows.Beans;

import java.io.Serializable;

/**
 * 兴趣标签对象
 */
public class My_Interests_Beans implements Serializable{
    private String Id;//加密id
    private String UserId;//加密UserId
    private String TagId;//加密tagid
    private String content;//标签内容
    private boolean flag;//选择标志

    public My_Interests_Beans(String id, String userId, String tagId, String content, boolean flag) {
        Id = id;
        UserId = userId;
        TagId = tagId;
        this.content = content;
        this.flag = flag;
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

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
