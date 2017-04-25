package com.multshows.Beans;

import java.io.Serializable;

/**
 * 互动消息未读消息实体类
 */
public class InterMessageData implements Serializable{
    private int Number;//未读数量
    private int Type;//消息类型 1 评论 2 点赞 3 打赏 4 鲜花

    public InterMessageData() {
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
