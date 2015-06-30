package org.marker.mushroom.dao;

import java.util.List;

import org.marker.mushroom.beans.Plugin;

public interface IPluginDao {

	
	
	/**
	 * 查询全部的内容模型
	 * @return List<Module> 集合
	 * */
	List<Plugin> queryAll();
}
