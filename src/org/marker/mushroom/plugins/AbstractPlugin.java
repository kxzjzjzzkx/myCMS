package org.marker.mushroom.plugins;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.holder.WebRealPathHolder;
import org.marker.mushroom.utils.FileTools;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;




/**
 * 抽象插件类
 * @author marker
 *
 */
public abstract class AbstractPlugin{
	
	
	protected ISupportDao commonDao;
	
	/** 请求响应相关的对象 */
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext application;
	
	
	
	/** freeMarker模版引擎配置 */
	private Configuration cfg;   
	
	/** 网站插件目录 */
	protected String basePluginsPath;
	
	
	/** 插件文件名目录(如果要读取模版，必须配置) */
	protected String pluginName;
	
	
	/**
	 * 抽象构造
	 * @param pluginName 插件名称
	 */
	public AbstractPlugin(String pluginName){
		this.pluginName = pluginName;
		cfg = new Configuration();
		cfg.setEncoding(Locale.CHINA, "utf-8");//设置本地字符集
		 try {
			cfg.setDirectoryForTemplateLoading(new File(WebRealPathHolder.REAL_PATH+"plugins"+File.separator)); // 指定一个加载模版的数据源
		} catch (IOException e) { 
			e.printStackTrace();
		}   
		 // 指定模版如何查看数据模型.这个话题是高级主题…   
		 // 你目前只需要知道这么用就可以了:   
		 cfg.setObjectWrapper(new DefaultObjectWrapper());
	}
	
	
	
	/**
	 * 初始化
	 */
	public void init() {
		this.request = ActionContext.getReq();
		this.response = ActionContext.getResp();
		this.application = ActionContext.getApplication();

	}
	
	
	
	/**
	 * 读取模版信息
	 * @param fileName 模版文件名称
	 * @return String 文件内容
	 * */
	protected String readTemplete(String fileName) throws IOException{
		String path = this.basePluginsPath+this.pluginName+File.separator+fileName;
		return FileTools.getFileContet(new File(path), FileTools.FILE_CHARACTER_UTF8);
	}

	
	
	/**
	 * 获取模版引擎封装引擎
	 * 
	 * */
	protected YLFMEngine getYLFMEngine(String namePath){
		try {
			return new YLFMEngine(cfg.getTemplate(pluginName+File.separator+namePath));
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 请求转发
	 * @param YLFMEngine 转发FreeMarker模版文件地址
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public void dispatcher(YLFMEngine engine){
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(engine.getResult());
			pw.flush();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	
	
	
	
	//后台二级菜单
	public abstract String menu();
	
 

	//提交数据处理方法
	public abstract String action();

	
	
	
	/**
	 * 获取当前页码
	 * @return int 页码
	 * */
	public int getCurrentPageNo(){
		HttpServletRequest request = ActionContext.getReq();
		String temp = request.getParameter("currentPageNo");
		if(temp != null && !"".equals(temp))
			return Integer.parseInt(temp);
		return  1;
	}
	
	/**
	 * 获取请求参数
	 * @param key
	 * @return
	 */
	public String getParameter(String key){
		HttpServletRequest request = ActionContext.getReq();
		return request.getParameter(key);
	}
 
	public void setBasePluginsPath(String basePluginsPath) {
		this.basePluginsPath = basePluginsPath;
	}
	/**
	 * 获取参数RID
	 * @return int id
	 * */
	public String getRid(){
		HttpServletRequest request = ActionContext.getReq();
		String temp = request.getParameter("rid");
		if(temp != null && !"".equals(temp))
			return temp;
		return "";
	}
	
	
	/**
	 * 向请求对象推入数据
	 * @param key 键
	 * @param value Object对象
	 * */
	protected void put(String key , Object value){
		HttpServletRequest request = ActionContext.getReq();
		request.setAttribute(key, value);
	}



	public ISupportDao getCommonDao() {
		return commonDao;
	}



	public void setCommonDao(ISupportDao commonDao) {
		this.commonDao = commonDao;
	}
	
	
	
	

}
