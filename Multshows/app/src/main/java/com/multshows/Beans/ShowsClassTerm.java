package com.multshows.Beans;

/**
 * 描述：作品分类
 * 作者：李萧萧
 * 时间：2016.10.14
 */
public class ShowsClassTerm {
    String ClassName;//加密收藏者userId
    String IsUse;//加密作者id
    String BeginTime;//记录开始时间
    String EndTime;//记录结束时间
    int PageIndex;//页码
    int PageSize;//每页显示条数

    public ShowsClassTerm() {
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getIsUse() {
        return IsUse;
    }

    public void setIsUse(String isUse) {
        IsUse = isUse;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }
}
