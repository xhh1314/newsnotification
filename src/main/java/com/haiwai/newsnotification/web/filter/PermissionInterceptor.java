package com.haiwai.newsnotification.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.haiwai.newsnotification.manage.util.ResponseWrite;



/**
 * 后台权限拦截器
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
public class PermissionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String uri=request.getRequestURI();
		String path=request.getContextPath();
		String uri1=uri.substring(path.length()+1);
		//System.out.println("截取path后的uri："+uri1);
		//根据路径判定该资源是否需要进行权限验证,这里是设置forePermission Controller下的方法都要拦截
		if(uri1.startsWith("back")){
		HttpSession session=request.getSession();
		if(session.getAttribute("user")!=null) {
			return true;
		} else{
			//这里对请求进行判断，看看是否是ajax请求，如果是ajax请求，则直接在拦截器这里给前端返回一个消息，然后再前端ajax函数里进行判断跳转到登录界面
			String XRequested=request.getHeader("X-Requested-With");
			if(XRequested!=null && XRequested.equals("XMLHttpRequest"))
			{
				ResponseWrite.write(response, "isAjax");
				return false;
			}
			else{
			request.setAttribute("previousUri", uri);
			//ModelAndView mav=new ModelAndView("user/login");
			request.getRequestDispatcher("/user/login").forward(request, response);
			return false;
			}
			}
		}
		else{//页面不需要权限，通过
			return true;
		}						
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
