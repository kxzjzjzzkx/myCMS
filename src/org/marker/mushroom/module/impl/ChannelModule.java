package org.marker.mushroom.module.impl;

import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.module.CommonModule;
import org.marker.mushroom.sql.Sql;

/**
 * 栏目的模型处理
 * 
 * @author marker
 * */
public class ChannelModule extends CommonModule {

	
	
	/**
	 * 前台标签生成SQL遇到该模型则调用模型内算法
	 * @param tableName 表名称
	 * */
	public StringBuilder doWebFront(String tableName, SQLDataEngine sde) {
		String prefix = DataBaseConfig.getInstance().get("mushroom.db.prefix");// 表前缀，如："yl_"
		StringBuilder sql = new StringBuilder();
		sql.append("select A.pid,A.id,A.name,A.template,A.hide,A.keywords,A.description,A.icon,A.rows,A.sort,A.module, concat('/cms?p=',A.url) 'url' from ");
		sql.append(prefix).append("channel ").append(Sql.QUERY_FOR_ALIAS);
		return sql;
	}

}
