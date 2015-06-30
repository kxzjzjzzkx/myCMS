package org.marker.mushroom.core;


/**
 * 系统相关的常量
 * 
 * */
public interface SystemStatic {
	
	
	/** 模型工厂Bean名称 */
	String SYSTEM_MODULE_FACTORY = "moduleFactory";
	
	/** 插件工厂Bean名称 */
	String SYSTEM_PLUGIN_FACTORY = "pluginFactory";
	
	/** CMS模板引擎Bean名称 */
	String SYSTEM_CMS_TEMPLATE = "cmstemplate";
	
	
	
	/* -------------------------------------------------
	 *                服务层Bean变量名称
	 * ------------------------------------------------- */
	 
	/** 栏目查询服务层 */
	String SYSTEM_CHANNEL_SERVICE = "channelService";
	/** 公共Dao服务层 */
	String SYSTEM_COMMON_SERVICE = "commonService";
	
}
