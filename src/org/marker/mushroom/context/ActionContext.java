package org.marker.mushroom.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 保存各种作用域，此类设计采用ThreadLocal模式并发处理。
 * 
 * @author marker
 * */
public class ActionContext {

	private static ServletContext application;

	/**
	 * 目前放在SpringAware中进行绑定的
	 * @param application2
	 * @see{org.marker.mushroom.holder.MushRoomInitBuildHolder}
	 */
	public static void currentThreadBindServletContext(
			ServletContext application2) {
		if (application == null) {
			ActionContext.application = application2;
		}
	}

	public static void currentThreadBindRequestAndResponse(
			HttpServletRequest request, HttpServletResponse response) {
		ActionScopeData scopeData = ActionScopeData.getInstance();
		scopeData.setRequest(request);
		scopeData.setResponse(response);
	}

	public static HttpServletRequest getReq() {
		return ActionScopeData.getInstance().getRequest();
	}

	public static HttpServletResponse getResp() {
		return ActionScopeData.getInstance().getResponse();
	}

	public static ServletContext getApplication() {
		return application;
	}

	public static void remove() {
		ActionScopeData.threadLocalData.remove();
	}

}

class ActionScopeData {

	private HttpServletRequest request;
	private HttpServletResponse response;
	static final ThreadLocal<ActionScopeData> threadLocalData = new ThreadLocal<ActionScopeData>();

	public static ActionScopeData getInstance() {
		ActionScopeData instance = threadLocalData.get();
		if (instance == null) {
			instance = new ActionScopeData();
			threadLocalData.set(instance);
		}
		return instance;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}