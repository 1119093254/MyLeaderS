package com.multshows.Beans;

import com.multshows.R;

import java.io.Serializable;

/**
 * 我的订单 beans
 */
public class My_OrderList_Beans implements Serializable{
    private int id;//订单id
    private String title;//订单标题
    private String photo;//订单图
    private String money;//商品价格
    private String state;//订单状态

    public My_OrderList_Beans(int id, String title, String photo, String money, String state) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.money = money;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
