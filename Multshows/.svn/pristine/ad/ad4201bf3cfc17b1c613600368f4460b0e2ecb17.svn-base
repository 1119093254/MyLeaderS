package com.multshows.Utils;

import android.util.Log;

import com.multshows.Views.MyApplication;

/**
 * 截取字符串
 */
public class SubString_Utils {
    /**
     * 2015-10-17T00:00:00+08:00  截取2015-10-17
     * @param str 字符串
     * @return
     */
    public static String getBirthday(String str){
        String[] strings=str.split("T");
       String subString=strings[0];
       return subString;
    }
    /**
     * 获取截取得到数组
     * @param str 字符串
     * @param flag 截取标志
     * @return
     */
    public static String[] getSubStringList(String str,String flag){
        String[] strings=str.split(flag);
        return strings;
    }
    /**
     * 获取图片名称添加_180*180   9GTFn9Gf.png————>9GTFn9Gf_1024x768.png
     * @param str 字符串
     * @return 返回图片路径 http://file.test.viigoo.com:3322/image/viigoo/9GTFn9Gf_1024x768.png
     */
    public static String getImageUrl(String str){
        MyApplication myApplication=new MyApplication();
        if(str!=null){
            if(str.length()>4) {
                String s = str.substring(str.length() - 4, str.length());
                if (".png".equalsIgnoreCase(s)) {
                    String[] strings = str.split(".png");
                    if (strings.length != 0) {
                        return myApplication.getImageUrl() + strings[0] + "_180x180" + ".png";
                    } else {
                        return "";
                    }
                } else if (".jpg".equalsIgnoreCase(s)) {
                    String[] strings_ = str.split(".jpg");
                    if (strings_.length != 0) {
                        return myApplication.getImageUrl() + strings_[0] + "_180x180" + ".jpg";
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }else {
                return " ";
            }
        }else  return " ";
    }
    /**
     * 获取图片名称添加_1024x768    9GTFn9Gf.png————>9GTFn9Gf_1024x768.png
     * @param str 字符串
     * @return 返回图片路径 http://file.test.viigoo.com:3322/image/viigoo/9GTFn9Gf_1024x768.png
     */
    public static String getImageUrlclearly(String str){
        MyApplication myApplication=new MyApplication();
        if(str!=null){
            if(str.length()>4) {
                String s = str.substring(str.length() - 4, str.length());
                if (".png".equalsIgnoreCase(s)) {
                    String[] strings = str.split(".png");
                    if (strings.length != 0) {
                        return myApplication.getImageUrl() + strings[0] + "_1024x768" + ".png";
                    } else {
                        return "";
                    }
                } else if (".jpg".equalsIgnoreCase(s)) {
                    String[] strings_ = str.split(".jpg");
                    if (strings_.length != 0) {
                        return myApplication.getImageUrl() + strings_[0] + "_1024x768" + ".jpg";
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }else {
                return " ";
            }
        }else  return " ";
    }
    /**
     * 获取图片名称添加_180*180    9GTFn9Gf.png————>9GTFn9Gf_1024x768.png
     * @param str 字符串
     * @return 返回图片路径 http://file.test.viigoo.com:3322/image/viigoo/9GTFn9Gf_1024x768.png
     */
    public static String getImageUrl3(String str){
        MyApplication myApplication=new MyApplication();
        if(str!=null){
            if(str.length()>4) {
                String s = str.substring(str.length() - 4, str.length());
                if (".png".equalsIgnoreCase(s)) {
                    String[] strings = str.split(".png");
                    if (strings.length != 0) {
                         //return myApplication.getImageUrl() + strings[0] + "_256x192" + ".png";
                        return myApplication.getImageUrl() + strings[0] + "_180x180" + ".png";
                    } else {
                        return "";
                    }
                } else if (".jpg".equalsIgnoreCase(s)) {
                    String[] strings_ = str.split(".jpg");
                    if (strings_.length != 0) {
                       // return myApplication.getImageUrl() + strings_[0] + "_512x512" + ".png";
                        return myApplication.getImageUrl() + strings_[0] + "_180x180" + ".jpg";
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }else {
                return " ";
            }
        }else  return " ";
    }
    /**
     * 获取图片名称添加_512x384    9GTFn9Gf.png————>9GTFn9Gf_1024x768.png
     * @param str 字符串
     * @return 返回图片路径 http://file.test.viigoo.com:3322/image/viigoo/9GTFn9Gf_1024x768.png
     */
    public static String getImageUrl4(String str){
        MyApplication myApplication=new MyApplication();
        if(str!=null){
            if(str.length()>4) {
                String s = str.substring(str.length() - 4, str.length());
                if (".png".equalsIgnoreCase(s)) {
                    String[] strings = str.split(".png");
                    if (strings.length != 0) {
                         return myApplication.getImageUrl() + strings[0] + "_512x384" + ".png";
                    } else {
                        return "";
                    }
                } else if (".jpg".equalsIgnoreCase(s)) {
                    String[] strings_ = str.split(".jpg");
                    if (strings_.length != 0) {
                       // return myApplication.getImageUrl() + strings_[0] + "_512x512" + ".png";
                        return myApplication.getImageUrl() + strings_[0] + "_512x384" + ".jpg";
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }else {
                return " ";
            }
        }else  return " ";
    }
    /**
     * 获取图片名称添加_100x100    9GTFn9Gf.png————>9GTFn9Gf_1024x768.png
     * @param str 字符串
     * @return 返回图片路径 http://file.test.viigoo.com:3322/image/viigoo/9GTFn9Gf_1024x768.png
     */
    public static String getImageUrl2(String str){
        MyApplication myApplication=new MyApplication();
        if(str!=null){
            if(str.length()>4) {
                String s = str.substring(str.length() - 4, str.length());
                if (".png".equalsIgnoreCase(s)) {
                    String[] strings = str.split(".png");
                    if (strings.length != 0) {
                        return myApplication.getImageUrl() + strings[0] + "_100x100" + ".png";
                    } else {
                        return "";
                    }
                } else if (".jpg".equalsIgnoreCase(s)) {
                    String[] strings_ = str.split(".jpg");
                    if (strings_.length != 0) {
                        return myApplication.getImageUrl() + strings_[0] + "_100x100" + ".jpg";
                    } else {
                        return "";
                    }
                } else {
                    return "http://file.test.viigoo.com:3322/image/leadershows/cU3pFcUd_180x180.png";
                }
            }else {
                return "http://file.test.viigoo.com:3322/image/leadershows/cU3pFcUd_180x180.png";
            }
        }else  return "http://file.test.viigoo.com:3322/image/leadershows/cU3pFcUd_180x180.png";


    }
    /**
     * 截取天 ，前
     * @param str 字符串
     * @return 返回
     */
    public static String[] getTimeUrl(String str){
        String[] strings=str.split(" ");
        if(strings.length!=0){
            return strings;
        }else {
            return null;
        }
    }
    /**
     * 截取 ，
     * @param str 字符串
     * @return 返回第一个图片
     */
    public static String getPhotoUrl(String str){
        String[] strings=str.split(",");
        if(strings.length!=0){
            return strings[0];
        }else {
            return "";
        }
    }

}
