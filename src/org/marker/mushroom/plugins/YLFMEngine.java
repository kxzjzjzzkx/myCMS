package org.marker.mushroom.plugins;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.utils.HttpUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;



/**
 * YLFreeMarker引擎
 * 封装了模版引擎的操作，减少插件开发者代码量
 * @author marker
 * */
public class YLFMEngine {

	private Template template;
	
	Map<String, Object> data = new HashMap<String, Object>();
	
	
	public YLFMEngine(Template template){
		this.template = template;
	}
	
	/**
	 * 传递数据到模版
	 * */
	public void put(String key, Object value){
		data.put(key, value);
	}
	
	
	
	
	/**
	 * 获取结果
	 * */
	public String getResult(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(baos);
		try {
			HttpServletRequest request = ActionContext.getReq();
			data.put(AppStatic.WEB_APP_URL, HttpUtils.getRequestURL(request));
			template.process(data, out);
			return new String(baos.toByteArray());
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}finally{
			try {
				baos.close();
				out.close();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	
}
