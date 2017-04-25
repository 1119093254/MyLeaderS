package com.multshows.Beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/29.
 */
public class SystemVersion implements Serializable{
    String Id;
    String VersionNnumber;
    String UpdateAddress;
    String RecordTime;
    String RecordTimeStr;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getVersionNnumber() {
        return VersionNnumber;
    }

    public void setVersionNnumber(String versionNnumber) {
        VersionNnumber = versionNnumber;
    }

    public String getUpdateAddress() {
        return UpdateAddress;
    }

    public void setUpdateAddress(String updateAddress) {
        UpdateAddress = updateAddress;
    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }

    public String getRecordTimeStr() {
        return RecordTimeStr;
    }

    public void setRecordTimeStr(String recordTimeStr) {
        RecordTimeStr = recordTimeStr;
    }
}
