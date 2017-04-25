package com.multshows.Beans;

import java.io.Serializable;

/**
 * 事件总线——地图
 */
public class MapEvent_Model implements Serializable{
    private String url;
    private PoiItem_Beans Idle_Place;

    public MapEvent_Model() {
    }

    public MapEvent_Model(String url) {
        this.url = url;
    }

    public PoiItem_Beans getIdle_Place() {
        return Idle_Place;
    }

    public void setIdle_Place(PoiItem_Beans idle_Place) {
        Idle_Place = idle_Place;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
