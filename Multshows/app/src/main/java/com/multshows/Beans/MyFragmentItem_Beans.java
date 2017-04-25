package com.multshows.Beans;

import java.io.Serializable;

/**
 * 我的
 */
public class MyFragmentItem_Beans implements Serializable{
    private int image;
    private String text;

    public MyFragmentItem_Beans(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
