package org.marker.mushroom.support;

import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.dao.ISupportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;




public class SupportController {

	
	@Autowired protected JdbcTemplate dao;
	 
	
	/** 自动注入通用Dao */
	@Autowired protected ISupportDao commonDao;
	
	/**
	 * viewPath为视图的目录
	 * */
	protected String viewPath;
	
	public String getPrefix(){
		return DataBaseConfig.getInstance().get("mushroom.db.prefix");//表前缀，如："yl_"
	}
}
