package org.marker.mushroom.template.tags.impl;

import org.marker.mushroom.template.tags.AbstractTag;
import org.marker.mushroom.template.tags.ITag;



/**
 * 插件调用标签
 * 
 * */
public class PluginTagImpl extends AbstractTag implements ITag{

	
	/** 默认构造 */
	public PluginTagImpl(){
		this.put("<!--\\s*\\{\\s*pulgin:\\s*name=\\((\\w+)\\)\\s*invoke=\\((\\w+)\\)\\s*\\}\\s*-->", 
				"<%=YLPluginFactory.getInstance().get(\"$1\").initialize(request,response,application).$2()%>",0);
	
	}
 
	
	//测试
	public static void main(String[] args) {
		ITag xx = new PluginTagImpl();
		xx.iniContent("<!--{pulgin: name=(comment) invoke=(adsads)}-->");
		xx.doTag();
		System.out.println("处理结果:"+xx.getContent());
	}
	
 

}
