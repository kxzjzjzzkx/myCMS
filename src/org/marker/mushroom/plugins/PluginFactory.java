package org.marker.mushroom.plugins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.beans.Plugin;
import org.marker.mushroom.core.SystemStatic;
import org.marker.mushroom.dao.IPluginDao;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.holder.WebRealPathHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
 


/**
 * 插件工厂,负责代理各种插件模型
 * 调用格式: <!-- {pulgin: name=(comment)}  -->
 * 
 * 替换调用： //初始化请求对象和响应对象
 * 
 *  
 * */ 
@Component(SystemStatic.SYSTEM_PLUGIN_FACTORY)
public class PluginFactory {

	@Autowired private IPluginDao pluginDao;
	
	@Autowired private ISupportDao commonDao;

	private boolean isInit = false;

	//网站插件目录
	private static String basePath = "";
	
	
	//存放插件集合
	public static Map<String, PluginMode> data = new HashMap<String, PluginMode>();
 
	
	public void ini(){
		basePath = WebRealPathHolder.REAL_PATH + "plugins\\";//初始化插件目录
		 
		//初始化插件模型
		try{
		List<Plugin> list = pluginDao.queryAll();
		if(list != null){
			for(Plugin plugin : list){
				if(plugin != null){
					PluginMode mode = createPluginMode(plugin.getUri());
					if(mode != null){
						mode.setBasePluginPath(basePath);
						mode.setCommonDao(commonDao);
						data.put(plugin.getMark(), mode);
						System.out.println("插件代理工厂："+plugin.getMark());
					}
				}
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 
	
	/**
	 * 根据参数名获取插件实例 
	 * */
	public PluginMode get(String name){
		isinit();
		if( data.containsKey(name) ){//判断数据中是否包含插件
			return data.get(name);
		}
		return null;
	}
	
	
	
	/**
	 * 写入插件实例
	 * @param mark
	 * @param className
	 */
	public void put(String mark, String className){
		data.put(mark, createPluginMode(className));
	}
	
	
	
	/**
	 * 移除插件
	 * @param mark
	 */
	public void remove(String mark){
		data.remove(mark);
	}
	

	
	private static PluginMode createPluginMode(String className){
		Class<?> clzz;
		try { 
			if(className != null && !"".equals(className)){
				clzz = Class.forName(className);
				return new PluginMode((AbstractPlugin)clzz.newInstance());
			}
		} catch ( Exception e) { 
			e.printStackTrace();
		} 
		return null;
	}
	


	/**
	 * 扩展功能菜单
	 * */
	public String getMenu() {
		StringBuffer sb = new StringBuffer();
		for(String mark : data.keySet()){
			PluginMode mode =  data.get(mark);
			sb.append(mode.invoke("menu")+"\n");
		} 
		return sb.toString();
	}
	
	
	
	 
		private void isinit(){
			if(!isInit ){
				 ini();
				 isInit = true;
			}
			
		}
		
}
