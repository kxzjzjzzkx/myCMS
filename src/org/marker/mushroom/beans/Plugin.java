package org.marker.mushroom.beans;

import java.io.Serializable;

import org.marker.mushroom.beans.an.Entity;


@Entity("plugin")
public class Plugin implements Serializable{
	private static final long serialVersionUID = -4808038290887993182L;

	private long id;
	private String name;
	private String uri;
	private short status;
	private String mark;
	private String author;
	private String ver;
	
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
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	
	
}
