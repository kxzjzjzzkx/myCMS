package org.marker.mushroom.plugins.impl;

import java.io.IOException;

import org.marker.mushroom.beans.Link;
import org.marker.mushroom.plugins.AbstractPlugin;
import org.marker.mushroom.plugins.YLFMEngine;

import freemarker.template.TemplateException;

public class FlinkPluginImpl extends AbstractPlugin{

	public static final String PLUGIN_NAME = "flink";
	
	public FlinkPluginImpl() {
		super(PLUGIN_NAME);
	}

	
	/**
	 * 添加友情链接
	 * */
	public void add(){
		YLFMEngine e = getYLFMEngine("add.html");
		dispatcher(e);
		return;
	}
	
	public String save(){ 
		String name = getParameter("name");
		String url  = getParameter("url");
		String description = getParameter("description"); 
		String sql = "insert into link(name,url,description,views) values(?,?,?,0)";
		int status = commonDao.update(sql, new Object[]{name, url, description});
		if(status > 0){
			return "{\"status\":\"true\",\"message\":\"添加成功!\"}";
		}else{
			return "{\"status\":\"false\",\"message\":\"添加失败!\"}";
		} 
	}
	
	
	//删除友情链接
	public String delete(){
		if(!commonDao.deleteByIds(Link.class, getRid())){
			return "{\"status\":\"true\",\"message\":\"删除成功!\"}";
 
		}else{
			return "{\"status\":\"false\",\"message\":\"删除失败!\"}";
		}
	}
	
	
	
	
	public void list(){
		YLFMEngine e = getYLFMEngine("list.html");
		e.put("page",  commonDao.findByPage(getCurrentPageNo(), 5, "select * from link order by id desc"));
		this.dispatcher(e);
	}
	
	
	
	public String show() throws TemplateException, IOException{
		YLFMEngine e = getYLFMEngine("index.html");
		
		e.put("links", commonDao.queryForList("select * from link order by id desc"));
		return e.getResult(); 
  
	}
	
	
	
	@Override
	public String menu() { 
		return null;
	}

	
	
	
	@Override
	public String action() { 
		return null;
	}
	
	
}
