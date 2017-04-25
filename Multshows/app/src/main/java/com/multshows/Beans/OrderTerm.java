package com.multshows.Beans;

import java.io.Serializable;

/**
 * 获取订单列表请求参数 序列化
 */
public class OrderTerm implements Serializable{
    private String Number;//订单编号
    private String State;//订单状态 0全部 1已取消 2已拍下/待付款 3已付款/待发货 4已发货/待收货 5已收货
    private  String BeginTime;//下单开始时间
    private  String EndTime;//下单结束时间
    private String BuyerId;//加密买家userId
    private String BuyerName;//卖家名称
    private String SellerId;//加密卖家UserId
    private String SellerName;//卖家名称
    private int PageIndex;//页码
    private int PageSize;//每页显示条数

    public OrderTerm() {
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
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

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
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
