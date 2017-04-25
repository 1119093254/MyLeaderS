package com.multshows.Beans;

import java.io.Serializable;

/**
 * 微信
 */
public class WxPrePay implements Serializable{
    private String appid;//应用APPID
    private String mch_id;//商户号
    private String nonce_str;//随机字符串
    private String prepay_id;//预支付交易会话标识
    private String sign_str;//签名
    private String package_str;//扩展字段
    private String timestamp_str;//时间戳

    public WxPrePay() {
    }

    @Override
    public String toString() {
        return "WxPrePay{" +
                "appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", sign_str='" + sign_str + '\'' +
                ", package_str='" + package_str + '\'' +
                ", timestamp_str='" + timestamp_str + '\'' +
                '}';
    }

    public String getSign_str() {
        return sign_str;
    }

    public void setSign_str(String sign_str) {
        this.sign_str = sign_str;
    }

    public String getPackage_str() {
        return package_str;
    }

    public void setPackage_str(String package_str) {
        this.package_str = package_str;
    }

    public String getTimestamp_str() {
        return timestamp_str;
    }

    public void setTimestamp_str(String timestamp_str) {
        this.timestamp_str = timestamp_str;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }


    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }
}
