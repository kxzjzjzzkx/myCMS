package org.marker.mushroom.template.tags;


/**
 * 自定义标签接口
 * @author marker
 * */
public interface ITag { 
	
	//初始化内容
	void iniContent(String content);
	//执行标签替换
	void doTag();
	
	/**
	 * 获取编译结果
	 * @return String 字符串
	 * */
	String getContent();
}
