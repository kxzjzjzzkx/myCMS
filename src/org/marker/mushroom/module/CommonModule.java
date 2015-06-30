package org.marker.mushroom.module;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.core.AppResolving;
import org.marker.mushroom.dao.ISupportDao;
import org.springframework.jdbc.core.JdbcTemplate;



/**
 * 抽象模型
 * 模型是拿来匹配和我发布文章或者是产品信息的时候用的，如果我发布一个商品，那么就会调用商品模型，这样区分出来
 * 
 * 
 * 
 * 
 * @author marker
 * 
 * */
public abstract class CommonModule implements IModule {
	

	/** 数据库模型引擎 */
	public JdbcTemplate dao;
	public ISupportDao commonDao;
	
	
	protected String type;//类型
	protected String name;//名称
	protected String template;//模板
	
	/**
	 * 请求中的数据
	 * 
	 * */
	public void doGet(HttpServletRequest request, ServletContext application,
			Channel currentChannel, AppResolving resv) {
	}

	/**
	 * 前台list标签要处理的查询
	 * 
	 * */
	public StringBuilder doWebFront(String tableName, SQLDataEngine sde) {
		return null;
	}

	public void doContent(HttpServletRequest request,
			ServletContext application, Channel currentChannel,
			AppResolving resv) {
	}
	
}
