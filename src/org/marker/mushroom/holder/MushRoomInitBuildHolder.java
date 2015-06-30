package org.marker.mushroom.holder;

import java.io.File;

import javax.servlet.ServletContext;

import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.config.IDefaultConfig;
import org.marker.mushroom.core.config.URLRewriteConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.springframework.web.context.ServletContextAware;



/**
 * 七彩蘑菇 建站系统初始化构建
 * 
 * @author marker
 * */
public class MushRoomInitBuildHolder implements ServletContextAware{

	
	public void setServletContext(ServletContext application) {
    	String BasePath = application.getRealPath(File.separator);//网站根目录路径
    	
    	boolean isInstall = isInstall(BasePath);
    	
    	//将网站安装状态写入全局变量(在之后的判断安装状态使用获取全局变量形式)
    	application.setAttribute(AppStatic.WEB_APP_INSTALL, isInstall);
    	
    	
    	
		/* 
		 * ============================================================
		 *               ActionContext bind 应用作用域
		 * ============================================================
		 */ 	
    	ActionContext.currentThreadBindServletContext(application);
    	
    	
    	
    	
		/* 
		 * ============================================================
		 *                初始化系统配置信息路径
		 * ============================================================
		 */
    	IDefaultConfig systemConfig = SystemConfig.getInstance();
    	application.setAttribute(AppStatic.WEB_APP_CONFIG, systemConfig.getProperties());
    	
    	
		/* 
		 * ============================================================
		 *               URLRewrite 初始化URL规则
		 * ============================================================
		 */
    	URLRewriteConfig.getInstance();
  
    	
    	

    	
    	
//		if(isInstall){
//			DataBaseConfig dbc = DataBaseConfig.getInstance();
//			C3p0DataSourceProvide c3p0 =  new C3p0DataSourceProvide(dbc.getDatabase());
//			EngineKit.setDataSource(c3p0.getDataSource());
//	    	//初始化插件工厂
//	    	YLPluginFactory.ini(application);
//	    	
//	    	//模型初始化
//	    	ModuleFactory.init();
//		}
    	
    	 
		 
	}
	
	
	/**
	 * 判断是否是否已安装(true:安装 false:未安装)
	 * @return boolean 状态
	 * */
	private boolean isInstall(String basePath){
		if(new File(basePath+"data"+File.separator+"install.lock").exists()){
			return true;
		}
		return false; 
	}

}
