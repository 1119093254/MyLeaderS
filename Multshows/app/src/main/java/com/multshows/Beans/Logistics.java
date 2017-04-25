package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * 物流信息实体类
 */
public class Logistics implements Serializable{
    private String mailNo;//
    private String update;//
    private String updateStr;//
    private String ret_code;//
    private boolean flag;//
    private int status;//
    private String tel;//
    private List<Logistics_item> data;//
    private String expSpellName;//
    private String expTextName;//

    public Logistics() {
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getUpdateStr() {
        return updateStr;
    }

    public void setUpdateStr(String updateStr) {
        this.updateStr = updateStr;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<Logistics_item> getData() {
        return data;
    }

    public void setData(List<Logistics_item> data) {
        this.data = data;
    }

    public String getExpSpellName() {
        return expSpellName;
    }

    public void setExpSpellName(String expSpellName) {
        this.expSpellName = expSpellName;
    }

    public String getExpTextName() {
        return expTextName;
    }

    public void setExpTextName(String expTextName) {
        this.expTextName = expTextName;
    }
}
