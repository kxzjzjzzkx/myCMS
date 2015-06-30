package org.marker.mushroom.template.tags.impl;

import org.marker.mushroom.template.tags.AbstractTag;
import org.marker.mushroom.template.tags.ITag;


/**
 * URL重写标签
 * 格式:  
 * 
 * */
public class URLRewriteTagImpl extends AbstractTag implements ITag {
	/** 默认构造 */
	public URLRewriteTagImpl(){
		//\\\\{?\\w+\\}?\\.?\\w*\\??[\\w+\\-?\\=?\\$?\\{?\\w+\\.\\}?&?]*
		this.put("href\\=[\"\']\\$\\{(\\w+.\\w+)\\}[\'\"]",
				"href=\"\\${url}\\${encoder($1)}\"",0);
		
	}
	

 

 
	
	public static void main(String[] args) {
		ITag xx = new URLRewriteTagImpl();
		
		xx.iniContent("<a href=\"${channel.url}\">我的飒沓</a> " +
				" \n<a href=\"news-${articles.prePageNo}\">我的飒沓</a> " +
				"\n <a href=\"news-${articles.prePageNo}\">我的飒沓</a>");
//		String xxx = "<a href=\"cms?p=index&type=a&id=${dsad.ads}\">dsad</a>";
//		xx.iniContent(xxx);
		xx.doTag();
		System.out.println(xx.getContent());
		
		 
	}

 


}
