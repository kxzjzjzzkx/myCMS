package org.marker.mushroom.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.YLAPP;

/**
 * 前台界面的Servlet 这个Servlet主要用来将请求转到YLAPP对象中，有核心处理类来处理前台的请求信息
 * 
 * @author marker
 * */
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 6700091564520406775L;

	/**
	 * 处理请求/cms?参数=值&
	 * @throws IOException 
	 * */
	public void progress(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 记录开始执行时间
		request.setAttribute(AppStatic.WEB_APP_STARTTIME,
				System.currentTimeMillis());
		// 设置响应编码
		response.setContentType("text/html;charset=utf-8");
		// 线程绑定请求对象和响应对象
		ActionContext.currentThreadBindRequestAndResponse(request, response);

		// 创建应用实例(多线程模式)
		YLAPP app = YLAPP.createInstance();
		app.start();// 启动实例

	}

	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.progress(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.progress(request, response);
	}
	
}
