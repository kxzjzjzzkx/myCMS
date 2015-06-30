package org.marker.mushroom.template.tags.impl;

import org.marker.mushroom.template.tags.AbstractTag;
import org.marker.mushroom.template.tags.ITag;


/**
 * URL绝对路径重写标签
 * 作用于主题目录：images、css、js、static文件夹
 * 格式:  
 * 
 * 
 * @author marker
 * */
public class AbsoluteURLTagImpl extends AbstractTag {

	
	/** 默认构造 */
	public AbsoluteURLTagImpl(){
		this.put(
				"(src=|href=|background=)[\"\']((?!http://)(?!\\$)(?!\\#).+)[\"\']",
				"$1\"\\${url}/\\${config.themes_path}$2\"",
				0);
//		this.put("<c:url value=", "\\${url}/<c:url value=", 0);
	}
	
 
  
	
	public static void main(String[] args) {
		ITag xx = new AbsoluteURLTagImpl();
		xx.iniContent("<link href=\"css/dsdsadsa/dsadsad/main.css\" \n" +
				" src=\"#\"></script>\n" +
				"<a href=\"http://dsadsa.png\"></a>" +
				" \n<a href=\"${decoder(article.url)}\">${c.name} </a>\n" +
				"href=\"${decoder(channel.url)}\">" +
				"\n<img src=\"${c.dsd}\"/> " +
				"<!--{if:${channel.id == current.id}}-->\n" +
			"<li><a href=\"${encoder(channel.url)}\"> </a></li>\n" + 
			"<li><a href=\"${encoder(channel.url)}\"><div>${channel.name}</div></a></li> ");
		xx.doTag();
		xx.doTag();
		System.out.println(xx.getContent()); 
	}

 


}
