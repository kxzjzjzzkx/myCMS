package org.marker.mushroom.core;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.module.IModuleFactory;
import org.marker.mushroom.template.MyCMSTemplate;
import org.marker.mushroom.utils.HttpUtils;

import freemarker.template.TemplateException;

/**
 * 前台处理核心对象
 * 
 * 应用控制类，完成网址解析，单一入口控制，静态页面缓存功能
 * 
 * @author marker
 * */

public final class YLAPP {

	private static final Log log = LogFactory.getLog(YLAPP.class);

	/** 请求响应相关的对象 */
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext application;

	/** 私有构造方法 ，避免外部被实例化 */
	private YLAPP() { }

	/**
	 * 创建APP实例
	 * 
	 * @return
	 */
	public static YLAPP createInstance() {
		return new YLAPP().init();
	}

	private YLAPP init() {
		this.request     = ActionContext.getReq();
		this.response    = ActionContext.getResp();
		this.application = ActionContext.getApplication();
		return this;
	}

	/**
	 * 启动App
	 * @throws IOException 
	 * */
	public void start() throws IOException {
		// 网址路径
		application.setAttribute(AppStatic.WEB_APP_URL,
				HttpUtils.getRequestURL(request));

		// 检查系统是否安装
		if (!(Boolean) application.getAttribute(AppStatic.WEB_APP_INSTALL)) {// 判断安装状态
			try {// 没有安装则进入安装页面
				log.info("cms not install!");
				response.sendRedirect("install/index.jsp");
				return;
			} catch (IOException e) {
				log.error("install.jsp not exist!", e);
			}
		}
		
		AppResolving resv = new AppResolving(request);// 解析地址

		try {
			/** 模型工厂模型处理 */
			IModuleFactory moduleFactory = SpringContextHolder
					.getBean(SystemStatic.SYSTEM_MODULE_FACTORY);
			moduleFactory.parse(resv);

			MyCMSTemplate cmstemplate = SpringContextHolder
					.getBean(SystemStatic.SYSTEM_CMS_TEMPLATE);
			// 代理编译
			cmstemplate.proxyCompile(resv.template);

			cmstemplate.sendModeltoView(resv.template);
		} catch (TemplateException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (IOException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} finally {
			this.destory();
		}

	}

	
	
	/**
	 * 销毁App
	 * */
	public void destory() {
		
		//清除当前请求对象和响应对象
		ActionContext.remove();
	}

}
