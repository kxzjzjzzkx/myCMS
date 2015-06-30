package org.marker.mushroom.plugins;

import java.util.List;

import org.marker.mushroom.core.SystemStatic;
import org.marker.mushroom.holder.SpringContextHolder;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;


/**
 * 插件调用标签(for freemarker) 
 * @author marker
 * */
public class PluginInvokeTag implements TemplateMethodModel {
 
 
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		StringBuilder str = new StringBuilder(); 
		if(args != null && args.size() > 1){
			String pluginMark = (String) args.get(0);
			String param1     = (String) args.get(1);
			PluginFactory pf = SpringContextHolder.getBean(SystemStatic.SYSTEM_PLUGIN_FACTORY);
	 
			PluginMode pm = pf.get(pluginMark);
			str.append(pm.invoke(param1));
		}else{//插件错误
			str.append("<!-- 插件调用错误！ -->");
		}
		return str;
	} 

}
