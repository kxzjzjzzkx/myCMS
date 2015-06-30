package org.marker.mushroom.plugins;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.utils.MethodNameFilter;



/**
 * 插件模型
 * 可处理各种插件的反射调用，内部属性存放插件实例的方法数组以便反射调用
 * @author marker
 * @date 2013-01-19
 * */
public class PluginMode {

	//插件实例对象
	private AbstractPlugin pluginObj;
 
	 

	
	/**
	 * 构造带有插件实例的插件模型
	 * @param pluginObj
	 */
	public PluginMode(AbstractPlugin pluginObj){
		this.setPluginObj(pluginObj);
	}
	
	
	
	
	
	
	
 
	
	
	
	
	
	/**
	 * 设置插件实现对象
	 * @param pluginObj 插件对象
	 * */
	public void setPluginObj(AbstractPlugin pluginObj) {
		this.pluginObj = pluginObj; 
	}

	
	
	/**
	 * 显示界面
	 * @param req
	 * @param resp
	 * @return
	 */
	public String show(){
		try {
			Method me = this.pluginObj.getClass().getMethod("show");
			return (String) me.invoke(pluginObj);
		} catch (SecurityException e1) { 
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) { 
			e1.printStackTrace();
		} catch (IllegalArgumentException e) { 
			e.printStackTrace();
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
		} catch (InvocationTargetException e) { 
			e.printStackTrace();
		} 
		
		return null; 
	}

 


 





	/**
	 * 调用指定方法
	 * @param methodName 方法名称
	 * 
	 * */
	public Object invoke(String methodName) {
		try {
			if( MethodNameFilter.checkMethodName(methodName) ){//方法名过滤（安全机制）
				Method me = this.pluginObj.getClass().getMethod(methodName);
				return me.invoke(this.pluginObj);
			} 
		} catch (IllegalArgumentException e) { 
			e.printStackTrace();
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
		} catch (InvocationTargetException e) { 
			e.printStackTrace();
		} catch (SecurityException e) { 
			e.printStackTrace();
		} catch (NoSuchMethodException e) { 
			e.printStackTrace();
		} 
		return null; 
	}
	
	
	
	
	public void setBasePluginPath(String path){
		
		try {
			Method me = this.pluginObj.getClass().getMethod("setBasePluginsPath",String.class);
			me.invoke(pluginObj,path);
		} catch (SecurityException e1) { 
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) { 
			e1.printStackTrace();
		} catch (IllegalArgumentException e) { 
			e.printStackTrace();
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
		} catch (InvocationTargetException e) { 
			e.printStackTrace();
		} 
	}







	public void action() {
		try {
			Method me = this.pluginObj.getClass().getMethod("action");
			me.invoke(pluginObj);
		} catch (SecurityException e1) { 
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) { 
			e1.printStackTrace();
		} catch (IllegalArgumentException e) { 
			e.printStackTrace();
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
		} catch (InvocationTargetException e) { 
			e.printStackTrace();
		} 
		
	}







	public void setCommonDao(ISupportDao commonDao) {
		try {
			Method me = this.pluginObj.getClass().getMethod("setCommonDao",ISupportDao.class);
			me.invoke(pluginObj,commonDao);
		} catch (SecurityException e1) { 
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) { 
			e1.printStackTrace();
		} catch (IllegalArgumentException e) { 
			e.printStackTrace();
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
		} catch (InvocationTargetException e) { 
			e.printStackTrace();
		} 
		
	}

	/**
	 * 初始化模型
	 * */
	public void init() {
		pluginObj.init();
	}
	
}
