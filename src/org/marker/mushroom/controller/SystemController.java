package org.marker.mushroom.controller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.config.IDefaultConfig;
import org.marker.mushroom.core.config.URLRewriteConfig;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.security.Base64;
import org.marker.mushroom.security.DES;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * RushRoom系统控制器
 * @author marker
 * */
@Controller
@RequestMapping("/admin/system")
public class SystemController extends SupportController {

	public SystemController() {
		this.viewPath = "/admin/system/";
	}
	 
	
	//网站基本信息
	@RequestMapping("/siteinfo")
	public String siteinfo(HttpServletRequest request){
		SystemConfig config = SystemConfig.getInstance();
		request.setAttribute("config", config.getProperties());
		return this.viewPath + "siteinfo";
	}
	
	
	//保存网站配置信息
	@ResponseBody
	@RequestMapping("/saveinfo")
	public Object saveinfo(HttpServletRequest request){
		IDefaultConfig config = SystemConfig.getInstance();
		try{
			/* 系统基本信息配置 */
			config.put("title", request.getParameter("config.title"));//网站标题
			config.put("url", request.getParameter("config.url"));//网站地址
			config.put("keywords", request.getParameter("config.keywords"));//网站关键字
			config.put("description", request.getParameter("config.description"));//网站描述
			config.put("mastermail", request.getParameter("config.mastermail"));//管理员邮箱
			config.put("copyright", request.getParameter("config.copyright"));//版权信息
			config.put("icp", request.getParameter("config.icp"));//ICP备案
			
			/* 主题配置 */
			config.put("index_page", request.getParameter("config.index_page"));//网站首页
			config.put("error_page", request.getParameter("config.error_page"));//错误模版
			config.put("themes_path", request.getParameter("config.themes_path"));//主题路径
			config.put("themes_cache", request.getParameter("config.themes_cache"));//主题缓存目录
			config.put("dev_mode", request.getParameter("config.dev_mode"));//是否开发模式
			config.put("gzip", request.getParameter("config.gzip"));//GZIP
			
			config.store();//修改配置信息状态
			return new ResultMessage(true, "更新成功!");
		}catch (Exception e) {
			return new ResultMessage(false, "更新失败!");
		} 
	}
	
	

	
	
	/**
	 * SEO设置
	 * */
	@RequestMapping("/seoinfo")
	public String seoinfo(HttpServletRequest request){
		URLRewriteConfig urlRewriteConfig =  URLRewriteConfig.getInstance();
		request.setAttribute("urlConfig", urlRewriteConfig.getProperties());
		return this.viewPath + "seoinfo";
	}
	
	@ResponseBody
	@RequestMapping("/saveseoinfo")
	public Object saveseoinfo(HttpServletRequest request){
		URLRewriteConfig urlRewriteConfig =  URLRewriteConfig.getInstance();
		try{
			String channelRule = request.getParameter("url.channel");
			String contentRule = request.getParameter("url.content");
			String pageRule    = request.getParameter("url.page");
			
			urlRewriteConfig.put("channel", channelRule);
			urlRewriteConfig.put("content", contentRule);
			urlRewriteConfig.put("page", pageRule);
			urlRewriteConfig.store();
			return new ResultMessage(true, "更新成功!");
		}catch (Exception e) {
			return new ResultMessage(true, "更新失败!");
		}
	}
	
	
	/**
	 * Mail配置
	 * */
	@RequestMapping("/mailinfo")
	public String mailinfo(HttpServletRequest request){ 
		return this.viewPath + "mailinfo";
	} 
	
	
	
	/**
	 * 进入数据库配置
	 * @param request
	 * @return
	 */
	@RequestMapping("/dbinfo")
	public ModelAndView dbinfo(HttpServletRequest request){
		ModelAndView view = new ModelAndView(this.viewPath + "dbinfo");
		IDefaultConfig dbconfig = DataBaseConfig.getInstance();
		Properties config = (Properties) dbconfig.getProperties().clone();
 
		String pass = config.getProperty("mushroom.db.pass");
		
		String desPass = getDesCode(pass);
		config.setProperty("mushroom.db.pass", desPass);
		
		view.addObject("sql", config);
		return view;
	}
	/**
	 * 获取Des加密结果
	 * */
	private String getDesCode(String pass){
		String key = SystemConfig.getInstance().get("secret_key");//网站秘钥，这是在安装的时候获取的
		try {
			return Base64.encode(DES.encrypt(pass.getBytes(), key));
		} catch (Exception e) { e.printStackTrace();}
		return pass;
	}
	/**
	 * 保存数据库配置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/savedbinfo")
	public Object savedbinfo(HttpServletRequest request){
		DataBaseConfig config = DataBaseConfig.getInstance();
		String oldPass = config.get("mushroom.db.pass");
		String newpass = request.getParameter("sql.pass");
		
		if(!getDesCode(oldPass).equals(newpass)){//修改密码了
			oldPass = newpass;
		}
		
		try{
			//数据库连接配置信息
			config.put("mushroom.db.host", request.getParameter("sql.host"));
			config.put("mushroom.db.port", request.getParameter("sql.port"));
			config.put("mushroom.db.demo", request.getParameter("sql.demo"));
			config.put("mushroom.db.char", request.getParameter("sql.char"));
			config.put("mushroom.db.debug", request.getParameter("sql.debug"));
			config.put("mushroom.db.prefix", request.getParameter("sql.prefix"));
			config.put("mushroom.db.driver", request.getParameter("sql.driver"));
			config.put("mushroom.db.user", request.getParameter("sql.user"));
			config.put("mushroom.db.pass", oldPass);
			
			//数据库连接池配置信息
			config.put("c3p0.initialPoolSize", request.getParameter("sql.initialPoolSize"));
			config.put("c3p0.minPoolSize", request.getParameter("sql.minPoolSize"));
			config.put("c3p0.maxPoolSize", request.getParameter("sql.maxPoolSize"));
			config.put("c3p0.acquireIncrement", request.getParameter("sql.acquireIncrement"));
			config.put("c3p0.maxIdleTime", request.getParameter("sql.maxIdleTime"));
			config.put("c3p0.maxStatements", request.getParameter("sql.maxStatements"));
			
			config.store();//持久化配置信息
			return new ResultMessage(true, "修改成功! 重启服务器生效!");
		}catch (Exception e) {
			return new ResultMessage(false, "更新失败!");
		}
		
	}
	
	
}
