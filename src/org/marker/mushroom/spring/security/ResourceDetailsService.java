package org.marker.mushroom.spring.security;

import java.util.LinkedHashMap;

/**
 * spring2.0支持数据库
 * RequestMap生成接口,由用户自行实现从数据库或其它地方查询URL-授权关系定义.
 * 
 * @author jayd
 * @since 1.0
 */
public interface ResourceDetailsService {

	/**
	 * 返回带顺序的URL-授权关系Map, key为受保护的URL,
	 * value为能访问该URL的授权名称列表,以','分隔.
	 */
	public LinkedHashMap<String, String> getRequestMap() throws Exception; //NOSONAR
}
