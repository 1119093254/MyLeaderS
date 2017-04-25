package com.lxx.domain;

import java.io.Serializable;

public class ResultBean implements Serializable {
	private int Code;
	private String Message;
	Object Template;
	
	public ResultBean(int code, String message, Object template) {
		super();
		Code = code;
		Message = message;
		Template = template;
	}
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Object getTemplate() {
		return Template;
	}
	public void setTemplate(Object template) {
		Template = template;
	}
	
}
