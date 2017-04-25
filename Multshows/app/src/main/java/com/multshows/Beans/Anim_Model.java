package com.multshows.Beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/25.
 */
public class Anim_Model implements Serializable{
    private String flags;

    public Anim_Model(String flags) {
        this.flags = flags;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }
}
