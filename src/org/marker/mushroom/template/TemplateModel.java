package org.marker.mushroom.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.marker.mushroom.beans.SQLDataEngine;


/**
 * 模板模型(保存模板文件对应的包含模板，以及读取时间)
 * @author marker
 * */
public class TemplateModel {

	private long readModified;
	
	private String freemarkerTpl;
	
	private List<File> files = new ArrayList<File>();
	
	
	private List<SQLDataEngine> sqls = new ArrayList<SQLDataEngine>();
	
	public long getReadModified() {
		return readModified;
	}

	public void setReadModified(long readModified) {
		this.readModified = readModified;
	}

	public String getFreemarkerTpl() {
		return freemarkerTpl;
	}

	public void setFreemarkerTpl(String freemarkerTpl) {
		this.freemarkerTpl = freemarkerTpl;
	}

	public List<SQLDataEngine> getSqls() {
		return sqls;
	}

	public void setSqls(List<SQLDataEngine> sqls) {
		this.sqls = sqls;
	}
	
	
	/**
	 * 获取模板修改时间
	 * */
	public long lastModified(){
		long time = 0l;
		for(File file : files){ time = time + file.lastModified(); }
		return time;
	}
	
	
	public void putTemplateFile(File file){
		this.files.add(file);
	}
	
	
}
