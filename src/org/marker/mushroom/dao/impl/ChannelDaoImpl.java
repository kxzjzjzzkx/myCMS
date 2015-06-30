package org.marker.mushroom.dao.impl;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.an.Entity;
import org.marker.mushroom.dao.AbstractCommonDao;
import org.marker.mushroom.dao.IChannelDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;


/**
 * ChannelDao
 * @author marker
 * */
@Repository("channelDao")
public class ChannelDaoImpl extends AbstractCommonDao implements IChannelDao{

	private final Log log = LogFactory.getLog(ChannelDaoImpl.class);
	
	
	
	public ChannelDaoImpl() {
		this.tableName = Channel.class.getAnnotation(Entity.class).value();
	}
	
	
	
	 //设定spring的ecache缓存策略,当编辑机构时候,把缓存全部清除掉,以达到缓存那数据同步;
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TriggersRemove(cacheName="channelCache", removeAll=true )
	public boolean update(Object entity) {
		return super.update(entity);
	}
	
	
	
	/**
	 * 通过url查询出对应的栏目信息，并缓存数据
	 * */
	@Cacheable(cacheName="channelCache")
	public Channel queryByUrl(String url) {
		String prefix = dbConfig.getPrefix();
		Channel channel = null;
		try{
			channel = jdbcTemplate.queryForObject("select concat('/cms?p=',c.url) url,c.* from "+prefix+"channel c where c.url=?", new Object[]{url}, new RowMapper<Channel>() {
				public Channel mapRow(ResultSet rs, int arg1) throws SQLException {
					Channel channel = new Channel();
					channel.setId(rs.getLong("id"));
					channel.setName(rs.getString("name"));
					channel.setTemplate(rs.getString("template"));
					channel.setModule(rs.getString("module"));
					channel.setUrl(rs.getString("url"));//URL地址
					channel.setPid(rs.getLong("pid"));
					channel.setRows(rs.getInt("rows"));//分页条数目
					channel.setIcon(rs.getString("icon"));//图标
					channel.setKeywords(rs.getString("keywords"));
					channel.setDescription(rs.getString("description"));
					System.out.println("" +this);
					return channel;
				}
			});
		}catch (Exception e) {
			log.error("数据库中不存在该栏目信息");
		}
		return channel;
	}



	public List<Channel> findAll() {
		String prefix = dbConfig.getPrefix();
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(prefix).append(tableName);
		return jdbcTemplate.query(sql.toString(),  new RowMapper<Channel>() {
			public Channel mapRow(ResultSet rs, int arg1) throws SQLException {
				Channel channel = new Channel();
				channel.setId(rs.getLong("id"));
				channel.setName(rs.getString("name"));
				channel.setTemplate(rs.getString("template"));
				channel.setModule(rs.getString("module"));
				channel.setUrl(rs.getString("url"));//URL地址
				channel.setPid(rs.getLong("pid"));
				channel.setRows(rs.getInt("rows"));//分页条数目
				channel.setIcon(rs.getString("icon"));//图标
				channel.setKeywords(rs.getString("keywords"));
				channel.setDescription(rs.getString("description"));
				return channel;
			}
		}); 
	}




	
	
}
