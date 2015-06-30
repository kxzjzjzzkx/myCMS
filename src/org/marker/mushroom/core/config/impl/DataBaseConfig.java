package org.marker.mushroom.core.config.impl;

import java.io.IOException;

import org.marker.mushroom.core.config.AbstractDefaultConfig;

/**
 * 数据库配置工具
 * 
 * @author marker
 * 
 * */
public class DataBaseConfig extends AbstractDefaultConfig {

	//表前缀变量
	private static final String VAR_PREFIX = "mushroom.db.prefix";
	
	
	/**
	 * 默认构造方法
	 * @throws IOException 
	 * */
	private DataBaseConfig() {
		this.configFilePath = DataBaseConfig.class.getResource(
				"/config/jdbc/connection.properties").getFile();
		try {
			super.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证。
	 * */
	private static class SingletonHolder {
		public final static DataBaseConfig instance = new DataBaseConfig();     
	}
	
	
	/**
	 * 获取数据库配置实例
	 * */
	public static DataBaseConfig getInstance(){
		return SingletonHolder.instance;
	}
	
	
	/**
	 * 获取表前缀
	 * */
	public String getPrefix(){
		return SingletonHolder.instance.get(VAR_PREFIX);
		
	}
	
	
	
}
