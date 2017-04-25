package com.multshows.Beans;

/**
 * 事件总线——聊天发图片
 */
public class OrderEvent_Model2 {
    private String url;
    private String Idle_Place;
    private String flag;
    private int position;

    public OrderEvent_Model2() {
    }

    public OrderEvent_Model2(String url, String idle_Place, int position, String flag) {
        this.url = url;
        Idle_Place = idle_Place;
        this.position = position;
        this.flag = flag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public OrderEvent_Model2(String url) {
        this.url = url;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIdle_Place() {
        return Idle_Place;
    }

    public void setIdle_Place(String idle_Place) {
        Idle_Place = idle_Place;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
