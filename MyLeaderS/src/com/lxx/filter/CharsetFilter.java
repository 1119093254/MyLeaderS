package com.lxx.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class CharsetFilter implements Filter {

	private FilterConfig fConfig=null;
	private String character="UTF-8";
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		//转换类型
		HttpServletRequest request=null;
		HttpServletResponse response=null;
		try {
			request = (HttpServletRequest)arg0;
			response=(HttpServletResponse)arg1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setCharacterEncoding(character);
		response.setCharacterEncoding(character);
		response.setContentType("text/html;charset="+character);
		//处理get
		HttpRequestWap requestWap = new HttpRequestWap(request);
		chain.doFilter(requestWap, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		fConfig = filterConfig;
		if(!fConfig.getInitParameter("character").equals("")){
			character=fConfig.getInitParameter("character");
		}
	}
	
	//get请求处理中文
	class HttpRequestWap extends HttpServletRequestWrapper{
		HttpServletRequest req=null;
		public HttpRequestWap(HttpServletRequest request) {
			super(request);
			req=request;
		}
		
		@Override
		public String getParameter(String name) {
			String values=req.getParameter(name);
			if(values!=null){
				try {
					values = new String(values.getBytes("ISO-8859-1"),super.getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return values;
			}else{
				return null;
			}
		}
		
	}
}