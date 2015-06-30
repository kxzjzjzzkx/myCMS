package org.marker.mushroom.template.tags.impl;

import org.marker.mushroom.template.tags.AbstractTag;
import org.marker.mushroom.template.tags.ITag;


/**
 * 执行时间标签
 * 调用格式:  <!--{executetime}-->
 * 
 * 已更新为Freemarker
 * @author marker
 * */
public class ExecuteTimeTagImpl extends AbstractTag implements ITag {

	
	/** 默认构造 */
	public ExecuteTimeTagImpl(){
		this.put("\\s*<!--\\s*\\{executetime\\}\\s*-->",
				"\\${.now?long-_starttime} ",0);
	}
 
	
	public static void main(String[] args) {
		ITag xx = new ExecuteTimeTagImpl();
		
		xx.iniContent("执行时间  <!--{executetime}--> 毫秒");
		xx.doTag();
		System.out.println(xx.getContent());
		
		 
	}

 



}
