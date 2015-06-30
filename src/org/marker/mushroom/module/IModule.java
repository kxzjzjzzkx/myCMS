package org.marker.mushroom.module;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.core.AppResolving;


/**
 * 模型接口
 * @author marker
 * */
public interface IModule {
	
	/**
	 * 处理处理Get请求的模型信息
	 * 
	 * */
	void doGet(HttpServletRequest request, ServletContext application, Channel currentChannel, AppResolving resv);
	
	
	
	/**
	 * Web前端生成SQL语句(模板引擎会调用来生成sql语句)
	 * @see SQLDataEngine
	 * */
	StringBuilder doWebFront(String tableName, SQLDataEngine sqlDataEngine);



	void doContent(HttpServletRequest request, ServletContext application, Channel currentChannel,
			AppResolving resv);
	
	
	
}
