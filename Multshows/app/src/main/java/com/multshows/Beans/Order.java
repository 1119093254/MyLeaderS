package com.multshows.Beans;

import java.io.Serializable;

/**
 * 订单实体 对象
 */
public class Order implements Serializable{
    private String Id;//加密Id
    private String OrderNumber;//订单编号
    private int PaymentType;//支付方式 1余额 2一麻袋
    private double Price;//价格
    private double RealPrice;//实付价格
    private String ExpressName;//快递名称
    private String ExpressNumber;//物流编号
    private double ExpressFee;//运费
    private int State;//订单状态 0全部 1已取消 2已拍下/待付款 3已付款/待发货 4已发货/待收货 5已收货
    private String StateName;//状态名称
    private String Remark;//备注
    private String OrderTime;//下单时间
    private int Count;//作品数量
    private UserBase Buyer;//UserBase 序列化参数
    private UserBase Seller;//UserBase 序列化参数
    private ShowsBase Shows;//ShowsBase 序列化参数
    private UserAddress Address;//UserAddressBase 序列化参数
    private double TransFee;//平台手续费

    public Order() {
    }

    public double getTransFee() {
        return TransFee;
    }

    public void setTransFee(double transFee) {
        TransFee = transFee;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public int getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(int paymentType) {
        PaymentType = paymentType;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getRealPrice() {
        return RealPrice;
    }

    public void setRealPrice(double realPrice) {
        RealPrice = realPrice;
    }

    public String getExpressName() {
        return ExpressName;
    }

    public void setExpressName(String expressName) {
        ExpressName = expressName;
    }

    public String getExpressNumber() {
        return ExpressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        ExpressNumber = expressNumber;
    }

    public double getExpressFee() {
        return ExpressFee;
    }

    public void setExpressFee(double expressFee) {
        ExpressFee = expressFee;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public UserBase getBuyer() {
        return Buyer;
    }

    public void setBuyer(UserBase buyer) {
        Buyer = buyer;
    }

    public UserBase getSeller() {
        return Seller;
    }

    public void setSeller(UserBase seller) {
        Seller = seller;
    }

    public ShowsBase getShows() {
        return Shows;
    }

    public void setShows(ShowsBase shows) {
        Shows = shows;
    }

    public UserAddress getAddress() {
        return Address;
    }

    public void setAddress(UserAddress address) {
        Address = address;
    }
}
