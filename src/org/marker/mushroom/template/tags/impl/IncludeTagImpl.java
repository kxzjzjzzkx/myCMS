package org.marker.mushroom.template.tags.impl;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.holder.WebRealPathHolder;
import org.marker.mushroom.template.tags.AbstractTag;
import org.marker.mushroom.template.tags.ITag;
import org.marker.mushroom.template.tags.MatchRule;
import org.marker.mushroom.utils.FileTools;


/**
 * 页面包含标签
 * 调用格式: <!-- include file="header.html" -->
 * @author marker
 * */
public class IncludeTagImpl extends AbstractTag implements ITag {

	private final SystemConfig config = SystemConfig.getInstance(); 
	
	/** 默认构造 */
	public IncludeTagImpl(){
		this.put("<!--\\s*#include\\s+file=[\"\'](.+)+[\'\"]\\s*-->",
				"<%@ include file=\"$1.jsp\" %>",0);
	}
	
 
	
	//重写此方法
	@Override
	public void doTag() {
		for(MatchRule mr : rules){
			Matcher m = mr.getRegex().matcher(content);
			while(m.find()){//找到两个
	    		String text = m.group();
	    		int a = text.indexOf("\"") + 1;
	    		int b = text.indexOf("\"", a);
	    		
	    		String start_tpl_path = WebRealPathHolder.REAL_PATH + config.get("themes_path") + text.substring(a, b);//原始模板文件路径
	    		
	    		File tplFile = new File(start_tpl_path);//模版文件
	    		
	    		
	    		try {
					String TempContent = FileTools.getFileContet(tplFile, FileTools.FILE_CHARACTER_UTF8);
					 
					content = content.replace(text, TempContent);
		 
				} catch (IOException e1) { 
					e1.printStackTrace();
				}
	    		
	    		//编译嵌套页面
			}
			
		}
	}
 

	public static void main(String[] args) {

		SystemConfig.getInstance();
		
		ITag xx = new IncludeTagImpl();
		String xxx = " <!--#include file=\"header.html\"--> " +
				"<!--#include file=\"fot.html\"-->";
		xx.iniContent(xxx);
		xx.doTag();
		System.out.println(xx.getContent());
		
		
		
		
	}


}
