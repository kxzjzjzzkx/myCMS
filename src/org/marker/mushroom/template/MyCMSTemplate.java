package org.marker.mushroom.template;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.SystemStatic;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.holder.WebRealPathHolder;
import org.marker.mushroom.plugins.PluginInvokeTag;
import org.marker.mushroom.template.tags.ITag;
import org.marker.mushroom.urlrewrite.UrlRewriteTag;
import org.marker.mushroom.utils.FileTools;
import org.marker.mushroom.utils.FileUtils;
import org.marker.mushroom.utils.ReadLine;
import org.marker.mushroom.utils.WebUtils;
import org.springframework.stereotype.Component;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;


/**
 * 模板引擎
 * @author marker
 * */
@Component(SystemStatic.SYSTEM_CMS_TEMPLATE)
public class MyCMSTemplate {
	
	private static final Log log = LogFactory.getLog(MyCMSTemplate.class);
	
	
	private static final Configuration config = new Configuration();
	
	
	static final StringTemplateLoader loader = new StringTemplateLoader();
	
	
	/** 系统配置信息 */
	private static final SystemConfig syscfg = SystemConfig.getInstance();
	//编码集(默认UTF-8)
	public static final String encoding = "utf-8";
	
	//本地语言(默认汉语)
	public static final Locale locale = Locale.CHINA;
	
	
	
	/** 模版标签库 */
	private static final List<ITag> tags = new ArrayList<ITag>();
	
  
	//临时存储sql数据引擎
	public List<SQLDataEngine> temp;
	
	//存放模版读取时间，为是否更新JSP提供依据
	private Map<String, TemplateModel> tplCache = new HashMap<String, TemplateModel>();
	
	
	
	//包含模板匹配模式
	private Pattern templatePattern = Pattern.compile("<!--\\s*#include\\s+file=[\"\'](.+)+[\'\"]\\s*-->");
	
	static{
		try {//加载标签数据
			String cfgFile = "tags"+File.separator+"res"+File.separator+"tags.res";
			String res = MyCMSTemplate.class.getResource(cfgFile).getFile();
			FileUtils.read(res, new ReadLine(){
				public void rendLine(String className) throws Exception {
					Class<?> clzz = Class.forName(className);
					tags.add((ITag)clzz.newInstance());
					log.info("create tag："+className);
				}
			}, FileTools.FILE_CHARACTER_UTF8);
			config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			config.setDefaultEncoding(encoding);  
		    config.setOutputEncoding(encoding);
			config.setEncoding(locale, encoding);//设置本地字符集
	        config.setLocale(locale);
	        config.setLocalizedLookup(false);
	        config.setTemplateLoader(loader);//设置模板加载器
		} catch (Exception e) {
			log.error("template tags init exception!", e);
		}
	}
	
	 
	
	
	
	/**
	 * 代理编译器
	 * */
	public void proxyCompile(String tplFileName) throws IOException, TemplateException{
		boolean isDevMode = Boolean.valueOf(syscfg.get("dev_mode"));
		
		//解析模版 
		String start_tpl_path = WebRealPathHolder.REAL_PATH + syscfg.get("themes_path") + tplFileName;//原始模板文件路径
		File tplFile = new File(start_tpl_path);//模板文件 
		
 
		//检查是否修改
		if(isDevMode){//如果是开发模式，每次获取都将会编译
			compile(tplFileName,tplFile);
		}else{
			TemplateModel tplModel = tplCache.get(tplFileName);
			if(tplModel != null){
				long rt = tplModel.getReadModified();//获取读取时间
				long mt = tplModel.lastModified();//获取修改时间
				if(mt > rt){ compile(tplFileName,tplFile); }//模板文件被修改了滴
			}else{
				compile(tplFileName,tplFile);
			}
		}
	}
	
	/**
	 * 编译
	 * @param 
	 * @throws IOException 
	 * */
	public synchronized void compile(String tplFileName, File tplFile) {
		log.info("compiling template file " + tplFileName);
		try {
			TemplateModel model = new TemplateModel();
			model.putTemplateFile(tplFile);//加入主要模板
			
			//第一步：加载模板内容
			String TempContent = FileTools.getFileContet(tplFile, FileTools.FILE_CHARACTER_UTF8);
			
			//第二步：包含模板解析，并把包含页面添加到当前内容页面里。 
			Matcher matcher = templatePattern.matcher(TempContent);
			while(matcher.find()){//找到两个
	    		String text = matcher.group();
	    		int a = text.indexOf("\"") + 1, b = text.indexOf("\"", a);
	    		File tplChidFile = new File( WebRealPathHolder.REAL_PATH + syscfg.get("themes_path") + text.substring(a, b));//模版文件
	    		model.putTemplateFile(tplChidFile);//向模板模型中加入包含的子页面
				String childTemplateContent = FileTools.getFileContet(tplChidFile, FileTools.FILE_CHARACTER_UTF8);
				TempContent = TempContent.replace(text, childTemplateContent);
			}
			this.temp = new ArrayList<SQLDataEngine>();//创建此模板页面的数据池
			//全部标签解析
			TempContent = system_tag_compile(TempContent);
			model.setReadModified(model.lastModified());
			model.setFreemarkerTpl(TempContent);
			model.setSqls(temp);
			
			//向模板加载器中写入模板信息
			loader.putTemplate(tplFileName, TempContent);
			tplCache.put(tplFileName, model);
			this.temp = null;
		
		} catch (IOException e) { 
			log.error("compiling template file " + tplFileName +" failed!", e);
		}
	}
	
	
 
	
	
	/**
	 * 将对象传递到view
	 * */
	public void sendModeltoView(String tpl) throws TemplateException, IOException{
		HttpServletRequest request   = ActionContext.getReq();
		HttpServletResponse response = ActionContext.getResp();
		ServletContext application   = ActionContext.getApplication();
		
		Map<String,Object> root = new HashMap<String,Object>(); 
		
		//URL重写
		root.put("encoder", new UrlRewriteTag());
		root.put("plugin",  new PluginInvokeTag());//插件调用
		
		@SuppressWarnings("unchecked")
		Enumeration<String> attrs3 = application.getAttributeNames(); 
		while (attrs3.hasMoreElements()) {
			String attrName = attrs3.nextElement();
			root.put(attrName, application.getAttribute(attrName));
		}
		//转移Session数据
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Enumeration<String> attrs2 = session.getAttributeNames();
		while (attrs2.hasMoreElements()) {
			String attrName = attrs2.nextElement();
			root.put(attrName, session.getAttribute(attrName));
		}
		//这里是进行数据转移
		@SuppressWarnings("unchecked")
		Enumeration<String> attrs = request.getAttributeNames(); 
		while (attrs.hasMoreElements()) {
			String attrName = attrs.nextElement();
			root.put(attrName, request.getAttribute(attrName));
		}

		//需要使用的组件准备就绪
		ISupportDao dao = SpringContextHolder.getBean("commonDao");
		
		
		for(SQLDataEngine dataTmp : getData(tpl)){//一个一个的数据提取策略
			if(dataTmp == null) continue;
		 
			String queryString = dataTmp.getQueryString();
			
			//获取当前栏目
			Channel current =  (Channel)request.getAttribute(AppStatic.WEB_CURRENT_CHANNEL);
			queryString = queryString.replaceAll("upid", current.getId()+"");
  
			
			
			root.put(dataTmp.getItems(), dao.queryFotList(dataTmp.getPage(),
					dataTmp.getLimit(), queryString));
		}
		
		Template template = config.getTemplate(tpl);

		
		
		
		Writer writer = null;
		boolean isGzip = Boolean.valueOf(syscfg.get("gzip"));//
		if(isGzip){//开启Gzip压缩
			if(WebUtils.checkAccetptGzip(request)){
				OutputStream os = WebUtils.buildGzipOutputStream(response);
				writer = new OutputStreamWriter(os,"utf-8");
			}else{
				writer = response.getWriter();
			}
		}else{
			writer = response.getWriter();
		}
		
		template.process(root, writer);
		writer.flush();
		writer.close();
	}







	
	
	/**
	 * 标记库替换
	 * @param content 内容
	 * @return String 
	 * */
	private String system_tag_compile(String content){
		for(ITag tag : tags){//遍历编译
			tag.iniContent(content);
			tag.doTag();
			content = tag.getContent();
		}
		return content;
	}



	/**
	 * 推送数据引擎到模版引擎
	 * 
	 * @param items 传递名称
	 * @param data2 数据引擎
	 * */
	public void put(SQLDataEngine data2) {
		data2.generateSql();//生成SQL语句
		temp.add(data2);
	}



		/**
	 * 获取数据引擎集合
	 * @return Map<String,SQLDataEngine> 集合
	 * */
	public List<SQLDataEngine> getData(String tpl){
		if(tplCache.containsKey(tpl)){
			return tplCache.get(tpl).getSqls();
		}
		return new ArrayList<SQLDataEngine>(0);
	}

}
