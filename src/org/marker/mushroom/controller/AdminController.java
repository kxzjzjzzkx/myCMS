package org.marker.mushroom.controller;

import java.io.IOException;
import java.util.Date;

import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.User;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.dao.IUserDao;
import org.marker.mushroom.interceptor.LoginInterceptor;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.GeneratePass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



/**
 * 后台管理主界面控制器
 * @author marker
 * 
 * */
@Controller
@RequestMapping("/admin")
public class AdminController extends SupportController {

	private final Log log = LogFactory.getLog(AdminController.class);
	
	@Autowired IUserDao userDao;
	
	/** 构造方法初始化一些成员变量 */
	public AdminController() {
		this.viewPath = "/admin/";
	}
	
	
	
	/** 后台主界面 */
	@Interceptors(LoginInterceptor.class)
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		StringBuilder url = new StringBuilder();//StringBuilder线程不安全，但这里只是局部变量不存在县城安全问题
		url.append(request.getScheme()).append("://").append(request.getServerName());
		if(request.getServerPort() != 80)  url.append(":").append(request.getServerPort());
		url.append(request.getContextPath());
		request.setAttribute("url", url); 
		return this.viewPath+"index" ;
	}
	
	
	//登录操作
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		return this.viewPath + "login";
	}
	
	
	
	
	/**
	 * 登录系统
	 * @return json
	 * */
	@ResponseBody
	@RequestMapping("/loginSystem")
	public Object loginSystem(HttpServletRequest request){
		String randcode = request.getParameter("randcode");//验证码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();//如果会话不存在也就不创建
		String scode = (String)session.getAttribute(AppStatic.WEB_APP_AUTH_CODE);
		if(scode != null && !scode.equals(randcode)){//验证码匹配
			return new ResultMessage(false,"验证码错误!");
		}else{
			password = GeneratePass.encode(password);
			User user = userDao.queryByNameAndPass(username, password);
			if(user != null){
				if(user.getStatus() == 1){//启用
					userDao.updateLoginTime(user.getId());//更新登录时间
					session.setAttribute(AppStatic.WEB_APP_SESSION_USEROBJECT, user); 
					session.setAttribute(AppStatic.WEB_APP_SESSSION_LOGINNAME, user.getName());
					return new ResultMessage(true,"登录成功!");
				}else{
					return new ResultMessage(false,"用户已禁止登录!");
				}
			}else{
				return new ResultMessage(false,"用户名或者密码错误!");
			}
		}
	}
	
	/**
	 * 注销
	 * */
	@RequestMapping("/logout")
	public void logout(HttpServletResponse response, HttpSession session){
		if(session != null) session.invalidate();
		try {
			response.sendRedirect("login.do");
		} catch (IOException e) {
			log.error("注销登录重定向异常", e);
		}
	}
	
	
	
	/**
	 * 系统信息
	 * */
	@RequestMapping("/systeminfo")
	public ModelAndView systeminfo(){
		ModelAndView view = new ModelAndView(this.viewPath + "systeminfo");
		String os = System.getProperty("os.name");//操作系统名称
		String osVer = System.getProperty("os.version"); //操作系统版本    
		String javaVer = System.getProperty("java.version"); //操作系统版本
		String javaVendor = System.getProperty("java.vendor"); //操作系统版本
		
		Runtime runTime = Runtime.getRuntime();
		
		long freeM = runTime.freeMemory() / 1024 / 1024;
        long maxM  = runTime.maxMemory() / 1024 / 1024;
        long tM    = runTime.totalMemory() / 1024 / 1024; 
        view.addObject("freememory", freeM);
        view.addObject("maxmemory", maxM);
        view.addObject("totalmemory", tM);
		view.addObject("os", os);
		view.addObject("osver", osVer);
		view.addObject("javaver", javaVer);
		view.addObject("javavendor", javaVendor);
		view.addObject("currenttime", new Date());
		view.addObject("serverinfo", "");
		view.addObject("dauthor", "marker");
		view.addObject("email", "wuweiit@gmail.com");
		view.addObject("version", "ver 20130531");
		view.addObject("qqqun","181150189");
		view.addObject("uxqqqun","181150189");
		
		return view;
	}
	
}
