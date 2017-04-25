package com.multshows.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：常用的验证函数
 * 作者：李萧萧
 * 时间：2016/7/7
 */
public class Verification_Utils {

    static boolean flag = false;
    static String regex = "";


    //判断网络是否连接
    public static boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int  n= 0; n < networkInfo.length; n++)
                {
                    System.out.println(n + "===状态===" + networkInfo[n].getState());
                    System.out.println(n+ "===类型===" + networkInfo[n].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[n].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 功能：验证邮箱
     * 参数：email 用户邮箱
     * 返回值：boolean
     */


    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     * 功能：验证手机号格式
     * 参数：mobileNumber 用户手机号
     * 返回值：boolean
     */
    public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
       /* try{
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0-9])|(17[6,7])|(14[5,7])|(17[0,3]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        if(mobileNumber.length()==11){
            if(mobileNumber.substring(0,3).equals("182")){
                flag=true;
            }
        }*/
        if(mobileNumber.length()==11){
            if(mobileNumber.substring(0,1).equals("1")){
                flag=true;
            }
        }
        return flag;
    }
    /**
     * 功能：验证输入身份证号
     * 参数：idCard 用户身份证号
     * 返回值：boolean
     */
    public static final String REGEX_ID_CARD = "^\\d{15}|^\\d{17}([0-9]|X|x)$";//身份证格式
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 功能：验证QQ号码
     * 参数：QQ 用户QQ号码
     * 返回值：boolean
     */
    public static boolean checkQQ(String QQ) {
        String regex = "^[1-9][0-9]{4,10}$";
        return check(QQ, regex);
    }
    /**
     * 功能：验证密码
     * 参数：password 用户密码
     * 返回值：boolean
     */
    public  static boolean CheckPassword(String password) {
        //匹配标识符必须由字母、数字、下划线组成，且开头和结尾不能有下划线
        String regex = "(^[a-z0-9A-Z])[a-z0-9A-Z_]+([a-z0-9-A-Z])";
        //匹配标识符必须由字母、数字、下划线组成，且开头和结尾不能有下划线,且中间的字符至少1个不能超过5个
        //String regex = "(^[a-z0-9A-Z])[a-z0-9A-Z_]{1,5}([a-z0-9-A-Z])";
        boolean flg = Pattern.matches(regex, password);
        System.out.println(flg);
        return flg;
    }
    /**
     * 功能：验证姓名
     * 参数：name 用户姓名
     * 返回值：boolean
     */
    public  static boolean CheckName(String name) {
        String regx = "([\\u4e00-\\u9fa5]*${2,8})";
        return Pattern.matches(regx, name);

    }  /**
     * 功能：验证昵称
     * 参数：name 用户昵称
     * 返回值：boolean
     */
    public  static boolean CheckuserName(String name) {
        String regx = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5]*$)+";
        return Pattern.matches(regx, name);

    }
    /**
     * 功能：验证户口
     * 参数：mregisteredResidence 用户户口
     * 返回值：boolean
     */
    public  static boolean CheckmregisteredResidence(String mregisteredResidence) {
        String regx = "^[0-9\\u4E00-\\u9FA5]+$";
        return Pattern.matches(regx, mregisteredResidence);

    }
    /**
     * 区号+座机号码+分机号码
     * @param fixedPhone
     * @return
     */
    public static boolean isFixedPhone(String fixedPhone){
        String reg="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }
    /**
     * 匹配中国邮政编码
     * @param postCode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPostCode(String postCode){
        String reg = "[1-9]\\d{5}";
        return Pattern.matches(reg, postCode);
    }
    public static boolean isId(String Id){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(Id).matches();
    }
    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
