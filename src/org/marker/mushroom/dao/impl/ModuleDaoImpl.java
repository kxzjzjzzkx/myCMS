package org.marker.mushroom.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.marker.mushroom.beans.Module;
import org.marker.mushroom.beans.an.Entity;
import org.marker.mushroom.dao.AbstractCommonDao;
import org.marker.mushroom.dao.IModuleDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
 

/**
 * 内容模型数据库操作类
 * @author marker
 * */
@Repository("moduleDao")
public class ModuleDaoImpl extends AbstractCommonDao implements IModuleDao {

	
	
	

	
	
	public ModuleDaoImpl() {
		this.tableName = Module.class.getAnnotation(Entity.class).value();
	}
	
	
	//查询全部的内容模型
	public List<Module> queryAll() {
		String prefix = dbConfig.getPrefix();
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(prefix).append(tableName);
		
	 
		return jdbcTemplate.query(sql.toString(), new RowMapper<Module>(){
			public Module mapRow(ResultSet rs, int num) throws SQLException {
				Module module = new Module();
				module.setId(rs.getLong("id"));
				module.setName(rs.getString("name"));
				module.setUri(rs.getString("uri"));
				module.setType(rs.getString("type"));
				module.setTemplate(rs.getString("template"));
				return module;
			}
		});
	}
	
	
	
}
