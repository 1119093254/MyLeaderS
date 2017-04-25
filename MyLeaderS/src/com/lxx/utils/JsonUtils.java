package com.lxx.utils;


public class JsonUtils {
public static String JsonString(int type,String Message,String Template) {
	String mString="{\"Code:\""+type+"\",\"Message:\""+Message+"\",Template:\""+Template+"\"}";
	return mString;
	}
}
