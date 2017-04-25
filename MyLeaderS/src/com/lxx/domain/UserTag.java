package com.lxx.domain;

import java.io.Serializable;

/**
 * 用户标签实体类
 */
public class UserTag implements Serializable{
    private String Id;//加密id
    private String UserId;//加密UserId
    private String TagId;//加密tagid
    private String TagName;//兴趣标签名称

    public UserTag(String userId, String tagId, String tagName) {
        UserId = userId;
        TagId = tagId;
        TagName = tagName;
    }

    public UserTag() {
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

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }
}
