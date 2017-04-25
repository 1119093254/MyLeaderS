package com.multshows.Beans;

import java.io.Serializable;

/**
 * 描述：找回密码实体类
 * 作者：李萧萧
 * 时间：2016.10.31
 */
public class FindPassWord implements Serializable{
    String Mobile;//手机号
    String Pwd;//密码
    int Type;//手机号

    public FindPassWord(String mobile, String pwd, int type) {
        Mobile = mobile;
        Pwd = pwd;
        Type = type;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
