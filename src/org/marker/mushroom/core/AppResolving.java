package org.marker.mushroom.core;

import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.core.config.impl.SystemConfig;



/**
 * 应用HTTP请求解析器
 * 
 * 
 * */
public class AppResolving { 
	
	public String pageName = "";//页面名称
	public String template = "";
	public String pageType     = null;//null代表栏目
	public String contentId = "0";
	public String page = "0";//页码
	
	/** 系统配置信息    */ 
	private SystemConfig config = SystemConfig.getInstance();
	
	
	public AppResolving(HttpServletRequest request){
		this.pageName = request.getParameter("p");//页面名称
		this.pageType = request.getParameter("type");//页面类型
		if(this.pageType == null){
			this.pageType = "channel";
		}
		if(!(this.pageName != null && !"".equals(this.pageName))){
			this.pageName = config.get("index_page");//获取默认主页地址
		}
		this.contentId = request.getParameter("id");//内容ID
		this.page      = request.getParameter("page");//页码
		//初始化模版页面（指向错误页面）
		this.template = config.get("error_page");
		
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("R:\n");
		sb.append("pageName="+pageName+"\n");
		sb.append("type="+pageType+"\n");
		sb.append("contentId="+contentId+"\n");
		sb.append("page="+page+"\n");
		
		return sb.toString();
	}
	
}
