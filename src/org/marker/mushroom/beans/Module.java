package org.marker.mushroom.beans;

import java.io.Serializable;

import org.marker.mushroom.beans.an.Entity;


/**
 * 内容模型
 * @author marker
 * */
@Entity("module")
public class Module implements Serializable{
	private static final long serialVersionUID = -4259189870337220290L;
	
	/** ID自动生成 */
	private long id;
	private String name;
	private String template;
	/** 类型 */
	private String type;
	private String uri;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	 
}
