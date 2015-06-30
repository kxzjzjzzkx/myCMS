package org.marker.mushroom.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.SystemStatic;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.plugins.PluginFactory;
import org.marker.mushroom.plugins.PluginMode;


/**
 * 插件处理Servlet接口
 * @author marker
 * */
public class PluginServlet extends HttpServlet{
	private static final long serialVersionUID = -4092036998435956627L;
 

	/**
	 * 处理插件请求，调用插件的某个方法
	 * 
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String plugin = request.getParameter("plugin");
		String method = request.getParameter("method");
		if(plugin != null && !"".equals(plugin)){
			
			PluginFactory f = SpringContextHolder.getBean(SystemStatic.SYSTEM_PLUGIN_FACTORY);
			PluginMode mode = f.get(plugin);
			if(mode != null){
				//处理提交数据
				ActionContext.currentThreadBindRequestAndResponse(request, response);
				mode.init();
				String o = (String) mode.invoke(method);
				if(o != null){
					Writer out = response.getWriter();
					out.write(o);
					out.flush();
					out.close();
				}
				
			}
			
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
  
}
