package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 事件总线——银行
 */
public class BankEvent_Model implements Serializable{
    private UserBank content;
    private String flag;

    public BankEvent_Model() {
    }

    public BankEvent_Model(UserBank content, String flag) {
        this.content = content;
        this.flag = flag;
    }

    public UserBank getContent() {
        return content;
    }

    public void setContent(UserBank content) {
        this.content = content;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
