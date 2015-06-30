package org.marker.mushroom.plugins.impl;

import java.io.IOException;

import org.marker.mushroom.beans.GuestBook;
import org.marker.mushroom.plugins.AbstractPlugin;
import org.marker.mushroom.plugins.YLFMEngine;



/**
 * 留言插件
 * @author marker 
 * 
 * */
public class GuestBookPluginImpl extends AbstractPlugin {
 
	public static final String PLUGIN_NAME = "guestbook";
	
	
	public GuestBookPluginImpl() {
		super(PLUGIN_NAME);
	}
	
	
	
	public void list(){
		YLFMEngine e = getYLFMEngine("guestbook_list.html");
		e.put("page",  commonDao.findByPage(getCurrentPageNo(), 5, "select * from guestbook order by id desc"));
		this.dispatcher(e);
	}
	
	
	//删除栏目
	public void delete(){
		YLFMEngine e = getYLFMEngine("_data.html"); 
		if(!commonDao.deleteByIds(GuestBook.class, getRid())){
			e.put("data", "{\"status\":\"false\",\"message\":\"删除失败\"}");
			this.dispatcher(e);
			return;
		}else{
			e.put("data", "{\"status\":\"true\"}"); 
			this.dispatcher(e);
			return;
		}
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
		YLFMEngine e = getYLFMEngine("index.html");
		return e.getResult();
	 
	}










	//处理提交数据
	@Override
	public String action() {
		String code = getParameter("authcode");//验证码 
		String randauthcode = (String) request.getSession().getAttribute("randauthcode");
		String ip  = request.getRemoteHost();
		String nickname = request.getParameter("nickname");
		String content  = request.getParameter("content");
		if(randauthcode != null && randauthcode.toLowerCase().equals(code.toLowerCase())){
			String sql = "insert into guestbook(nickname,ip,content,time) values(?,?,?,sysdate())";
			commonDao.update(sql, new Object[]{nickname,ip,content});	
		}
		try {
			response.sendRedirect("/"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
