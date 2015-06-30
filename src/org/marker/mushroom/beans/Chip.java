package org.marker.mushroom.beans;

import java.io.Serializable;

import org.marker.mushroom.beans.an.Entity;


/**
 * 碎片管理
 * @author marker
 * */
@Entity("chip")
public class Chip implements Serializable{
	private static final long serialVersionUID = -5515992661487193474L;
	
	private long id;
	private String name;
	private String mark;
	private String content;
	
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
 
	
	
}
