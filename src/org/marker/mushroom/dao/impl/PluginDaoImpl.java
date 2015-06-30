package org.marker.mushroom.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.marker.mushroom.beans.Plugin;
import org.marker.mushroom.beans.an.Entity;
import org.marker.mushroom.dao.AbstractCommonDao;
import org.marker.mushroom.dao.IPluginDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 * 插件数据库操作对象
 * @author marker
 * */
@Repository("pluginDao")
public class PluginDaoImpl extends AbstractCommonDao implements IPluginDao{

	
	public PluginDaoImpl() {
		this.tableName = Plugin.class.getAnnotation(Entity.class).value();
	}

	
	
	
	public List<Plugin> queryAll() {
		String prefix = dbConfig.getPrefix();
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(prefix).append(tableName);
		return jdbcTemplate.query(sql.toString(), new RowMapper<Plugin>(){
			public Plugin mapRow(ResultSet rs, int num) throws SQLException {
				Plugin plugin = new Plugin();
				plugin.setId(rs.getLong("id"));
				plugin.setName(rs.getString("name"));
				plugin.setUri(rs.getString("uri"));
				plugin.setMark(rs.getString("mark"));
				plugin.setStatus(rs.getShort("status"));
				return plugin;
			}
		}); 
	}
	
	
	
	 
}
