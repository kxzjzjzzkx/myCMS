package org.marker.mushroom.template.tags.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.template.MyCMSTemplate;
import org.marker.mushroom.template.tags.AbstractTag;
import org.marker.mushroom.template.tags.ITag;
import org.marker.mushroom.template.tags.MatchRule;


/**
 * 遍历集合标签
 * 说明：采用解析结构生成SQL语句进行进一步的查询，最后使用MVC模式传递数据
 * 格式：
 * <code>
 *  <!-- {list:var=(channel) items=(channels) table=(channel) pid=(0) page=(1) limit=(10)} --> 
 *		<a href="${channel.url}">${channel.name}</a>
 *	<!-- {/list} -->
 * </code>
 * 2013-01-18: 调整了输出HTML代码的格式，增加可读性
 * 2013-01-20 根据item参数来存取SQLDataEngqin
 * 
 * @author marker
 * @date 2013-01-17
 */
public class ListTagImpl extends AbstractTag {

	/** 默认构造 */
	public ListTagImpl(){
		this.put("\\s*<!--\\s*\\{list:var\\=\\((\\w+)\\)\\s*items\\=\\((\\w+)\\)\\s*table\\=\\((\\w+)\\)\\s*[\\w+\\=\\('?\\w+\\x20?\\d*\\w+'?\\)\\s*]*\\}\\s*-->[\\x20]*\\n?",
				"<#list $2 as $1>\n",1);
		this.put("\\s*<!--\\s*?\\{/list\\}\\s*?-->[\\x20]*\\n?", 
				"</#list>\n", 0);
	}
	
	
	
	@Override
	public void doTag() {
		for(MatchRule mr : rules){ 
			if(mr.getType() == 0){
				content = mr.getRegex().matcher(content).replaceAll(mr.getReplace());
			}else if(mr.getType() == 1){
				MyCMSTemplate cmstemplate = SpringContextHolder.getBean("cmstemplate");
				Matcher m = mr.getRegex().matcher(content);
				while(m.find()){
					//处理提取数据.进行持久化操作
					String text = m.group();
		    		//解析数据库相关字段表名等。
					int sql_start = text.indexOf(":")+1;
					int sql_end   = text.lastIndexOf("}");
					text = text.substring(sql_start, sql_end); 
					Pattern p_a = Pattern.compile("\\w+\\=\\('?\\w*\\x20?\\d*\\w*'?"); // 将给定的正则表达式编译到模式中
					Matcher m_a = p_a.matcher(text);
					
					
					//创建数据引擎
					SQLDataEngine data = new SQLDataEngine();
					String whereTemp = "";//必须初始化""
					while(m_a.find()){  
						String[] field_kv = m_a.group().split("\\=\\(");//拆分数据格式 ："var=(xxxx"
						if("table".equals(field_kv[0])) {
							data.setTableName(field_kv[1]);
							continue;
						}else if("var".equals(field_kv[0])) {//遍历内部对象变量名称，参考forEach标签
							data.setVar(field_kv[1]);
							continue;
						}else if("items".equals(field_kv[0])) {//数据集合名称
							data.setItems(field_kv[1]);
							continue;
						}else if("limit".equals(field_kv[0])) {//数据量限制
							data.setLimit(Integer.parseInt(field_kv[1]));
							continue;
						}else if("page".equals(field_kv[0])){//查询页码 
							data.setPage(Integer.parseInt(field_kv[1]));
							continue;
						}else if("order".equals(field_kv[0])){//排序支持
							data.setOrder(field_kv[1]);
						}else {//Where条件
							whereTemp += field_kv[0]+"="+field_kv[1]+",";
						}
					}
					 
					data.setWhere(whereTemp);//设置条件
					
					cmstemplate.put(data);  
				}
				content = m.replaceAll(mr.getReplace());//匹配替换
			}
		}
	}
 
	
	public static void main(String[] args) {
		ITag xx = new ListTagImpl();
		String xxx = "    \n   <!-- {list:var=(xxxx) items=(xxxxs) table=(channel) pid=(0) url=('index')  limit=(10) order=(id desc)} -->       \n      dsads";
		xxx  +=" \n   <!-- {/list} -->";
		xx.iniContent(xxx);
		xx.doTag();
		System.out.println(xx.getContent());
		
	} 

}
