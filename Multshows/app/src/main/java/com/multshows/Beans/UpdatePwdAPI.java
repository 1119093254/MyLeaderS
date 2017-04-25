package com.multshows.Beans;

import java.io.Serializable;

/**
 * 修改密码实体类
 */
public class UpdatePwdAPI implements Serializable{
    private String accountId;//加密accountId
    private String oldPwd;//旧密码
    private String newPwd;//新密码
    private int type;//密码修改类型 1登录密码2切换密码

    public UpdatePwdAPI() {}


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
