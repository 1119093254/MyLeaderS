package com.multshows.Beans;

import java.io.Serializable;

/**
 * 修改订单物流信息参数实体
 */
public class OrderLogistics implements Serializable{
    private String OrderId;//订单Id
    private String ExpressName;//快递名称
    private String ExpressNumber;//物流编号

    public OrderLogistics() {
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
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
}
