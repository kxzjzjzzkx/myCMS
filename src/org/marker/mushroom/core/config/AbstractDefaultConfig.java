package org.marker.mushroom.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 抽象默认配置类
 * @author marker
 * */
public abstract class AbstractDefaultConfig implements IDefaultConfig {
	
	private final Log log = LogFactory.getLog(AbstractDefaultConfig.class);
	
	/** 
	 * 配置信息存放对象
	 *(API: Properties类是线程安全的：多个线程可以共享单个Properties对象而无需进行外部同步)
	 * */
	protected Properties properties = new Properties();
	
	/** 配置文件路径 */
	protected String configFilePath;
	
	
	/**
	 * 获取配置字符串
	 */
	public String get(String key) {
		return properties.getProperty(key);
	}

	
	/**
	 * 设置配置key=value
	 */
	public void put(String key, String value) {
		properties.put(key, value);
	}

	
	
	public Properties getProperties() {
		return this.properties;
	}

	protected void init() throws IOException {
		if(checkConfigFileExist()){
			FileInputStream in = new FileInputStream(this.configFilePath);
			InputStreamReader isr = new InputStreamReader(in, "utf-8");
			this.properties.load(isr);//读取配置文件
			isr.close();
			in.close();
		}
	}

	



	public void store() throws IOException {
		if(checkConfigFileExist()){
			OutputStream out = new FileOutputStream(new File(configFilePath));
			OutputStreamWriter osw = new OutputStreamWriter(out, "utf-8");
			this.properties.store(osw, "Power By Marker .YLCMS 2013");
			osw.close();
			out.close();
		}
	}

	
	
	/**
	 * 检查配置文件是否存在
	 * */
	private boolean checkConfigFileExist() {
		if(configFilePath != null) return true;
		log.error("配置文件路径 configFilePath = null");
		return false;
	}
}
