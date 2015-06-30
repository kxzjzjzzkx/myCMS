package org.marker.mushroom.plugins.impl;

import org.marker.mushroom.plugins.AbstractPlugin;

public class BannerPluginImpl extends AbstractPlugin {

	
	 
	
	public BannerPluginImpl() {
		super("banner");
	}

	//后台二级菜单
	@Override
	public String menu() {
		String menuHTML = "";
		menuHTML += "<dl>";
		menuHTML += "<dt onclick=\"addMenuSub(this);\">幻灯片管理<div></div></dt>";
		menuHTML += "<dd><a href=\"#\" onclick=\"addContent(this);\" id=\"plugin/install.do\">幻灯片图片管理</a></dd>";
		menuHTML += "<dd><a href=\"#\" onclick=\"addContent(this);\" id=\"plugin/list.do\">幻灯片设置</a></dd>";
		menuHTML += "</dl>";
		return menuHTML;
	}
 
	public String show() { 
		return null;
	}

	@Override
	public String action() {
		
		
		
		
		
		
		
		
		return null;
	}
	
	
}
