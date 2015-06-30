package org.marker.mushroom.dao;

import java.util.List;

import org.marker.mushroom.beans.Module;




public interface IModuleDao {

	
	/**
	 * 查询全部的内容模型
	 * @return List<Module> 集合
	 * */
	List<Module> queryAll();
}
