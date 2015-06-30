package org.marker.mushroom.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.marker.mushroom.core.AppStatic;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 判断是否登录，如果没有登录就重定向到/admin/login.do
 * 
 * @author marker
 * 
 * */
public class LoginInterceptor implements HandlerInterceptor  {


	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute(AppStatic.WEB_APP_SESSSION_LOGINNAME);
		if(username != null){
			return true;
		}else{
			response.sendRedirect("/admin/login.do");
			return false;
		}
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
