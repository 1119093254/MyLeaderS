package com.multshows.Beans;

import java.io.Serializable;

/**
 * 高德地图周边POI信息对象
 */
public class PoiItem_Beans implements Serializable{
    private String PoiId;//POI 的id，即其唯一标识
    private String PoiTitle;//POI 的名称
    private String PoiSnippet;//POI 的地址
    private double latitude;//POI 经度
    private double longitude;//POI 纬度
    private String PoiProvince;//POI 省名称
    private String PoiAdName;//POI 市名称
    private String PoiCity;//POI 行政区名称
    private int PoiDistance;//POI 距离中心点的距离
    private boolean flag;//是否被选中

    public PoiItem_Beans() {
    }

    public PoiItem_Beans(String poiId, String poiTitle, String poiSnippet, double latitude,
                         double longitude, String poiProvince, String poiCity, String PoiAdName,int poiDistance,
                         boolean flag) {
        PoiId = poiId;
        PoiTitle = poiTitle;
        PoiSnippet = poiSnippet;
        this.latitude = latitude;
        this.longitude = longitude;
        PoiProvince = poiProvince;
        PoiCity = poiCity;
        this.PoiAdName=PoiAdName;
        PoiDistance = poiDistance;
        this.flag = flag;
    }

    public String getPoiAdName() {
        return PoiAdName;
    }

    public void setPoiAdName(String poiAdName) {
        PoiAdName = poiAdName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getPoiId() {
        return PoiId;
    }

    public void setPoiId(String poiId) {
        PoiId = poiId;
    }

    public String getPoiTitle() {
        return PoiTitle;
    }

    public void setPoiTitle(String poiTitle) {
        PoiTitle = poiTitle;
    }

    public String getPoiSnippet() {
        return PoiSnippet;
    }

    public void setPoiSnippet(String poiSnippet) {
        PoiSnippet = poiSnippet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPoiProvince() {
        return PoiProvince;
    }

    public void setPoiProvince(String poiProvince) {
        PoiProvince = poiProvince;
    }

    public String getPoiCity() {
        return PoiCity;
    }

    public void setPoiCity(String poiCity) {
        PoiCity = poiCity;
    }

    public int getPoiDistance() {
        return PoiDistance;
    }

    public void setPoiDistance(int poiDistance) {
        PoiDistance = poiDistance;
    }

    @Override
    public String toString() {
        return "PoiItem_Beans{" +
                "PoiId='" + PoiId + '\'' +
                ", PoiTitle='" + PoiTitle + '\'' +
                ", PoiSnippet='" + PoiSnippet + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", PoiProvince='" + PoiProvince + '\'' +
                ", PoiCity='" + PoiCity + '\'' +
                ", PoiDistance=" + PoiDistance +
                ", flag=" + flag +
                '}';
    }
}
