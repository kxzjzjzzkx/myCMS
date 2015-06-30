package org.marker.mushroom.template.tags.impl;

import org.marker.mushroom.template.tags.AbstractTag;
import org.marker.mushroom.template.tags.ITag;


/**
 * 时间格式标签
 * 调用格式: ${dsad format=(yy-DD-MM)}
 * 
 * 
 * @author marker
 * */
public class FormatDateTagImpl extends AbstractTag implements ITag {
	/** 默认构造 */
	public FormatDateTagImpl(){
		this.put("\\$\\{\\s*([a-z.]+)\\s+format\\=\\(([a-z A-Z:-]+)\\)\\s*\\}", 
				"<\\#if $1?exists>\\${$1?string(\"$2\")}</\\#if>",0); 
		
	}
	
 

 
	//时间格式化测试
	public static void main(String[] args) {
		ITag xx = new FormatDateTagImpl();
		
		xx.iniContent("${dsad.dsa format=(yy-DD-MM HH:mm:ss)} dsadasd");
		xx.doTag();
		System.out.println(xx.getContent()); 
		
	}



}
