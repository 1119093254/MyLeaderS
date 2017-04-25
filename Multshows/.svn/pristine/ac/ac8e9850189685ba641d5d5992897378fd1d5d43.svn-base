package com.multshows.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 描述：保存/获取数据到偏好设置
 * 作者：李萧萧
 * 时间：2016.10.17
 */
public class SaveSharedPreferences {
    private static final String PREFS_SAVENAME = "multshows_save";
    private SharedPreferences mSharedPreferences = null;
    //保存数据
    public void Save_PREFS(Context context,String name,String value){
        mSharedPreferences=context.getSharedPreferences(PREFS_SAVENAME, Context.MODE_PRIVATE);//模式为私有,只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容
        SharedPreferences.Editor editor = mSharedPreferences.edit();// 获取编辑器
        editor.putString(name, value);
        editor.commit();
    }
    //获取数据
    public String Get_PREFS(Context context,String name){
        mSharedPreferences=context.getSharedPreferences(PREFS_SAVENAME, Context.MODE_PRIVATE);//模式为私有,只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容
        return  mSharedPreferences.getString(name, "");
    }
}
