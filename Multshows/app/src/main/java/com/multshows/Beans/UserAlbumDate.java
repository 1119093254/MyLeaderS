package com.multshows.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class UserAlbumDate implements Serializable{
    String RecordTime;//时间
    List<UserAlbum> AlbumList;//用户相册集合

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }

    public List<UserAlbum> getAlbumList() {
        return AlbumList;
    }

    public void setAlbumList(List<UserAlbum> albumList) {
        AlbumList = albumList;
    }
}
