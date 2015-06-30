package org.marker.mushroom.core.config;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.urlrewrite.Parameter;
import org.marker.urlrewrite.URLRewrite;

public class URLRewriteConfig extends AbstractDefaultConfig {
	

	private final Log log = LogFactory.getLog(URLRewriteConfig.class);
	
	/**
	 * 默认构造方法
	 * 
	 * @throws IOException
	 * */
	private URLRewriteConfig() {
		this.configFilePath = DataBaseConfig.class.getResource(
				"/config/urlrewrite/urlrewrite.properties").getFile();
		try {
			initParameter();// 初始化规则参数

			super.init();// 加载配置文件

			// 写入URL规则
			for (Object obj : this.properties.keySet()) {
				String key = obj.toString();
				String rule = this.properties.getProperty(key);
				URLRewrite.me().putRule(key, rule);
				log.info("init rule： " + key + "=" + rule);
			}
		} catch (IOException e) {
			log.error("urlrewrite init error", e);
		}
	}
	
	
	/**
	 * 这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证。
	 * */
	private static class SingletonHolder {
		public final static URLRewriteConfig instance = new URLRewriteConfig();     
	}
	
	
	/**
	 * 获取系统配置实例
	 * */
	public static URLRewriteConfig getInstance(){
		return SingletonHolder.instance;
	}
	
	 
	
	
	/**
	 * 初始化所有配置文件 
	 * */
	private void initParameter(){  
		//设置规则参数
		URLRewrite.me().baseUrl = "/cms?";
		URLRewrite.me().put(new Parameter("p","{channel}","((?!admin)[a-zA-Z_]+)"));
		URLRewrite.me().put(new Parameter("type","{type}","([a-zA-Z_]+)"));
		URLRewrite.me().put(new Parameter("id","{id}","([0-9]+)"));
		URLRewrite.me().put(new Parameter("time","{time}","([0-9]+)"));
		URLRewrite.me().put(new Parameter("page","{page}","([0-9]+)"));
	}
	
	 
}
