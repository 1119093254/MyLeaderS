package com.multshows.Beans;

import java.io.Serializable;

/**
 * 上传图片实体类
 */
public class ImageInfo implements Serializable{
    private int[] FileData;//上传文件二进制数据
    private String Ext;//扩展名
    private boolean IsTemp;//是否临时文件
    private int MaxWidth;//最大宽度
    private int MaxHeight;//最大高度

    public ImageInfo() {
    }

    public int[] getFileData() {
        return FileData;
    }

    public void setFileData(int[] fileData) {
        FileData = fileData;
    }

    public String getExt() {
        return Ext;
    }

    public void setExt(String ext) {
        Ext = ext;
    }

    public boolean isTemp() {
        return IsTemp;
    }

    public void setTemp(boolean temp) {
        IsTemp = temp;
    }

    public int getMaxWidth() {
        return MaxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        MaxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return MaxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        MaxHeight = maxHeight;
    }
}
