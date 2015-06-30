package org.marker.mushroom.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.marker.mushroom.beans.User;
import org.marker.mushroom.beans.an.Entity;
import org.marker.mushroom.dao.AbstractCommonDao;
import org.marker.mushroom.dao.IUserDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("userDao")
public class UserDaoImpl extends AbstractCommonDao implements IUserDao{

	
	public UserDaoImpl() {
		this.tableName = User.class.getAnnotation(Entity.class).value();
	}
	
	
	/**
	 * 通过用户名和密码查询用户对象
	 * */
	public User queryByNameAndPass(String name, String pass) {
		String prefix = dbConfig.getPrefix();
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(prefix).append(tableName).append(" where name=? and pass=?");
		User user = null; 
		try{
			user = this.jdbcTemplate.queryForObject(sql.toString(), new Object[]{name, pass}, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int num) throws SQLException {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setNickname(rs.getString("nickname"));
				user.setLogintime(rs.getDate("logintime"));
				user.setStatus(rs.getShort("status"));
				return user;
			}
		});
		}catch (Exception e) {}
		
		return user;
	}


	public boolean updateLoginTime(Serializable id) {
		String prefix = dbConfig.getPrefix();
		StringBuilder sql = new StringBuilder("update ");
		sql.append(prefix).append("user ").append("set logintime=sysdate() where id=?");
		
		int status = jdbcTemplate.update(sql.toString(), id);
		return status>0?true:false;
	}

}
