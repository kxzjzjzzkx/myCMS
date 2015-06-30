package org.marker.mushroom.spring.security;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

//import com.mywish.mywish.domain.user.Resource;
//import com.mywish.mywish.service.ResourceManager;

/**
 * spring2.0支持数据库
 * 从数据库查询URL--授权定义Map的实现类.
 * 
 * @author jayd
 * @since 1.0
 */
@Transactional(readOnly = true)
public class ResourceDetailsServiceImpl implements ResourceDetailsService {

	public LinkedHashMap<String, String> getRequestMap() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

//	private ResourceManager resourceManager;
//
//	public void setResourceManager(ResourceManager resourceManager) {
//		this.resourceManager = resourceManager;
//	}

	/**
	 * @see ResourceDetailsService#getRequestMap()
	 */
//	public LinkedHashMap<String, String> getRequestMap() throws Exception {
//		List<Resource> resourceList = resourceManager.getUrlResourceWithAuthorities();
//
//		LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>(resourceList.size());
//		for (Resource resource : resourceList) {
//			requestMap.put(resource.getRes_string(), resource.getAuthNames());
//		}
//		return requestMap;
//	}
}
