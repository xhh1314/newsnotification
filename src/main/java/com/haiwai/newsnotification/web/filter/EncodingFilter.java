package com.haiwai.newsnotification.web.filter;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截所有请求，解决中文乱码问题
 * @author lh
 * @time 2017年10月25日
 * @version 1.0 
 */
public class EncodingFilter implements HandlerInterceptor {

	/**
	 * 字符编码
	 */
	private final String ENCODING = "UTF-8";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding(ENCODING);
		response.setCharacterEncoding(ENCODING);
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		//System.out.println("uri:"+uri);
		String ch = URLDecoder.decode(uri, ENCODING);
		//System.out.println("解码后的uri："+ch);
		if (uri.equals(ch)) {
			return true;
		}
		ch = ch.substring(req.getContextPath().length());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}


}
