package com.multshows.Utils;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* 描述：表情过滤转换
 * 11/15
 * 李萧萧
        */
public class Emoji_Change{
    /**
     *表情过滤
     */
    InputFilter emojiFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //Matcher emojiMatcher = emoji.matcher(source);
            String[] strs=emoji.split(source);
            String s="";
            for (int i=0;i<strs.length;i++){
                Matcher emojiMatcher = emoji.matcher(strs[i]);
                if (emojiMatcher.find()) {
                    strs[i]="[em:m001]";
                }
                s=s+strs[i];
            }

            return s;

        }
    };
    //emoji转换文字匹配符封装函数

    public String EmojiChange(String mString){
        List<Integer> mSet=new ArrayList<Integer>();
        mSet.clear();

        mSet=Emoji_Map.Key_Value();
        for (int i=0;i<mSet.size();i++) {
            mString = mString.replace(getEmoji(mSet.get(i)),
                    Emoji_Map.mMap.get(mSet.get(i)));
        }
        String str=emojiFilter.filter(mString,0,mString.length()-1,new SpannedString(""),0,0).toString();
        CLog.e("str",str);
        return str;
    }
    //获取表情
    public String getEmoji(int code){
        return new String(Character.toChars(code));
    }


    //emoji转换文字匹配符封装函数
    public String ChangeEmoji(String mString){
//        List<String> mSet2 = new ArrayList<String>();
//        mSet2.clear();
//        mSet2 = Emoji_Map.Key_StringValue();
//        for (int i = 0; i < mSet2.size(); i++) {
//           mString = mString.replace(Emoji_Map.mMapString.get(mSet2.get(i)), mSet2.get(i));
//        }
//        for(String value:mSet2){
//
//        }
        Emoji_Map em =new Emoji_Map();
        return em.Key_StringValue2(mString);
    }

    //emoji转换文字匹配符封装函数
    public String ChangeEmoji(SpannableString mString){
//        List<String> mSet2 = new ArrayList<String>();
//        mSet2.clear();
//        mSet2 = Emoji_Map.Key_StringValue();
//        for (int i = 0; i < mSet2.size(); i++) {
//           mString = mString.replace(Emoji_Map.mMapString.get(mSet2.get(i)), mSet2.get(i));
//        }
//        for(String value:mSet2){
//
//        }
        String mm=mString.toString();
        Emoji_Map em =new Emoji_Map();
        return em.Key_StringValue2(mm);
    }

    public String ChangeEmoji3(String mString){

           // Emoji_Map.Key_StringValue2(mString);

        return null;
    }

    //emoji转换文字匹配符封装函数
    public String ChangeEmoji2(String mString){
        StringBuilder sb = new StringBuilder();
        List<String> mSet2 = new ArrayList<String>();
        mSet2.clear();
        mSet2 = Emoji_Map.Key_StringValue();
        for (int i = 0; i < mSet2.size(); i++) {
            mString = mString.replace(Emoji_Map.mMapString.get(mSet2.get(i)), mSet2.get(i));
        }
        return mString;
    }
}
