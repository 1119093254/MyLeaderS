package com.multshows.Utils;

import android.util.Base64;

/**
 * Base64加密解密
 */
public class Base64_Utils {
    /**
     * 加密
     * @return
     */
    public static String encrypt(String s){
        String str= Base64.encodeToString(s.getBytes(), Base64.NO_WRAP);//DEFAULT
        return str;
    }
    /**
     * 解密
     * @return
     */
    public static String decrypt(String strBase64){
        String str = new String(Base64.decode(strBase64.getBytes(), Base64.NO_WRAP));
        return str;
    }
}
