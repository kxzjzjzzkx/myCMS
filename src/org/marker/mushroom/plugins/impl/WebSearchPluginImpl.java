package org.marker.mushroom.plugins.impl;

import java.io.IOException;
import java.net.URLDecoder;

import org.marker.mushroom.plugins.AbstractPlugin;
import org.marker.mushroom.plugins.YLFMEngine;



/**
 * Web搜索插件
 * @author marker
 * 
 * power by http://www.yl-blog.com
 * 
 * */
public class WebSearchPluginImpl extends AbstractPlugin {
 
	
	public WebSearchPluginImpl() {
		super("websearch"); 
	}
	
	
	
	public void list(){
		YLFMEngine e = getYLFMEngine("search_list.html"); 
 
		e.put("page",  commonDao.findByPage(getCurrentPageNo(), 5, "select * from searchinfo order by id desc"));
		this.dispatcher(e); 
		
	}
	
 
	
	

	//后台二级菜单
	@Override
	public String menu() {
		String menuHTML = "";
		try {
			menuHTML += readTemplete("admin_menu.html");
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return menuHTML;
	}
	
	
	
	

	
	
	//前台显示界面 
	public String show() { 
		
		String keyword = getParameter("keyword");
		keyword = URLDecoder .decode(keyword);
		String keyword2 = '%'+URLDecoder .decode(keyword)+'%';
		String ip = request.getRemoteHost(); 
	  
		request.setAttribute("page", commonDao.findByPage(getCurrentPageNo(), 10, 
				"select * from article where title like ?",new Object[]{keyword2}));
		
		return "";
	}










	//处理提交数据
	@Override
	public String action() { 
  
		
		
		
		
		return null;
	}










 
	
}
