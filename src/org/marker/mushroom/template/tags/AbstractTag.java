package org.marker.mushroom.template.tags;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签抽象类
 * 标签在编译模版文件的时候才会使用到
 * @author marker
 * @date 2012-12-04
 * */
public abstract class AbstractTag implements ITag {

	//匹配规则数组
	protected List<MatchRule> rules = new ArrayList<MatchRule>(1);
 
	//要替换的字符串
	protected String content;
 
	
	
	/** 默认构造 */
	public AbstractTag(){ }
	
	
	public void iniContent(String content) {
		this.content = content;
	}
	
	
	
	/**
	 * 添加匹配规则
	 * @param regex   正则表达式
	 * @param replace 替换内容
	 * @param type 类型：1.有数据  | 0.没数据 
	 * */
	public void put(String regex, String replace, int type){
		MatchRule mr = new MatchRule(regex, replace, type);
 
		rules.add(mr);
	}
	
	
	
	//默认替换支持
	public void doTag() {
		for(MatchRule mr : rules){
			content = mr.getRegex().matcher(content).replaceAll(mr.getReplace());
		} 
	}

	public String getContent() {
		return content;
	}
 
	
}
