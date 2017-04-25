package com.multshows.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 封装一些通用解析  类
 */
public class Json_Utils {
    /**
     * 解析这类格式的json字符串  {"Code":1006,"Message":"原始密码错误"}
     * @param json
     * @return
     * @throws JSONException
     */
    public static String getMessage(String json) throws JSONException {
        JSONObject jsonObject=new JSONObject(json);
        String message=jsonObject.getString("Message");
        return message;
    }
    /**
     * 解析这类格式的json字符串  {"Code":1006,"Message":"原始密码错误"}
     * @param json
     * @return
     * @throws JSONException
     */
    public static int getCode(String json) throws JSONException {
        JSONObject jsonObject=new JSONObject(json);
        int message=jsonObject.getInt("Code");
        return message;
    }
    /**
     * 解析这类格式的json字符串  {"Code":0,"Message":"Ok","Template":"{\"AccountId\"}}
     * @param json
     * @return
     * @throws JSONException
     */
    public static String getTemplate(String json) throws JSONException {
        JSONObject jsonObject=new JSONObject(json);
        String message=jsonObject.getString("Template");
        return message;
    }
    /**
     * 解析这类格式的json字符串
     * @param json
     * @return
     * @throws JSONException
     */
    public static int getCount(String json) throws JSONException {
        JSONObject jsonObject=new JSONObject(json);
        int message=jsonObject.getInt("Count");
        return message;
    }
    /**
     * 解析这类格式的json字符串  {"IsUploadSuc":true,"Message":"1W2oFc1Wg.png"}
     * @param json
     * @return
     * @throws JSONException
     */
    public static boolean getIsUploadSuc(String json) throws JSONException {
        JSONObject jsonObject=new JSONObject(json);
        boolean message=jsonObject.getBoolean("IsUploadSuc");
        return message;
    }
}
