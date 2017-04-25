package com.multshows.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述：获取当前时间、把毫秒转化为时间
 * 作者：贾强胜
 * 时间：2016.9.18
 */
public class Time_Now {
    public static int getNowTime(){
        int now = (int) (System.currentTimeMillis() / 1000);
        CLog.e("testing","当前时间："+now+"//"+System.currentTimeMillis());
        return now;
    }
    public static String getTime(){
        long now = System.currentTimeMillis();
        //输入毫秒数，转化为日期，用calendar方法；
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(now);
        int year = calendar2.get(Calendar.YEAR);
        int month = calendar2.get(Calendar.MONTH);
        int day = calendar2.get(Calendar.DAY_OF_MONTH);
        int hour = calendar2.get(Calendar.HOUR_OF_DAY);//24小时制
        //      int hour = calendar2.get(Calendar.HOUR);//12小时制
        int minute = calendar2.get(Calendar.MINUTE);
        int second = calendar2.get(Calendar.SECOND);

        if(hour<12){
            hour=hour+12;
        }else {
            hour=hour-12;
        }
        String now_time=year+"-"+(month + 1)+"-"+day+" "+hour+":"+minute+":"+second;
        return now_time;
    }
    public static String getRongTime(){
        long now = System.currentTimeMillis();
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(now));

    }
    public static String getRongTime(long time){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));

    }
    public static String getTime(long time){
        //输入毫秒数，转化为日期，用calendar方法；
       Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(time);
        int year = calendar2.get(Calendar.YEAR);
        int month = calendar2.get(Calendar.MONTH);
        int day = calendar2.get(Calendar.DAY_OF_MONTH);
        int hour = calendar2.get(Calendar.HOUR_OF_DAY);//24小时制
        //      int hour = calendar2.get(Calendar.HOUR);//12小时制
        int minute = calendar2.get(Calendar.MINUTE);
        int second = calendar2.get(Calendar.SECOND);

        if(hour<12){
            hour=hour+12;
        }else {
            hour=hour-12;
        }
        String now_time=year+"-"+(month + 1)+"-"+day+" "+hour+":"+minute+":"+second;
        return now_time;
    }
    public static String getYearTime(long time){
        //输入毫秒数，转化为日期，用calendar方法；
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        CLog.e("testing","时间戳转日期"+time+"///"+sdf.format(new Date(Long.valueOf(time+"000"))));
        return sdf.format(new Date(Long.valueOf(time+"000")));
    }
    //li
    public static int  getLongTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(millionSeconds);
        return (int) (millionSeconds/1000);
    }
    public static int getIntTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(millionSeconds/1000+"");
    }
    public static int getIntYearTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CLog.e("testing","生日转换时间："+(int) (millionSeconds/1000));
        return (int) (millionSeconds/1000);
    }

    //li
    public static String getStringTime(int time){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(time+"000")));

    }
    //详细年月日
    public static String getMessageTime(int time,String flag){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date=sdf.format(new Date(Long.valueOf(time+"000")));
        CLog.e("nowTime",""+date);
        String[] s=date.split(" ");
        String[] s_=new String[3];
        String[] t=new String[3];
        if(s.length>=2) {
            s_ = s[0].split("-");
            t = s[1].split(":");
        }
        String d="";
        if(s_.length>=3) {
            if ("year".equals(flag)) {
                d = s_[0];
            } else if ("month".equals(flag)) {
                d = s_[1];
            } else if ("day".equals(flag)) {
                d = s_[2];
            }
        }
        if(t.length>=3) {
            if ("hour".equals(flag)) {
                d = t[0];
            } else if ("minute".equals(flag)) {
                d = t[1];
            } else if ("second".equals(flag)) {
                d = t[2];
            }
        }
        return d;

    }

}
