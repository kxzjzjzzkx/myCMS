package org.marker.mushroom.core.config.impl;

import java.io.IOException;

import org.marker.mushroom.core.config.AbstractDefaultConfig;
 

/**
 * 系统配置类（对Properties进行了简单封装）
 * 用于配置系统配置文件，提供读取和保存两种持久化操作
 * 在系统StartListener监听器中进行配置文件地址的初始化
 * @author marker
 * */
public final class SystemConfig extends AbstractDefaultConfig{

 
	
	
	/**
	 * 默认构造方法
	 * @throws IOException 
	 * */
	private SystemConfig() {
		this.configFilePath = DataBaseConfig.class.getResource(
				"/config/site/system.config").getFile();
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
		public final static SystemConfig instance = new SystemConfig();     
	}
	
	
	/**
	 * 获取系统配置实例
	 * */
	public static SystemConfig getInstance(){
		return SingletonHolder.instance;
	}
	
}
