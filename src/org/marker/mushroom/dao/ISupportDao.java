package org.marker.mushroom.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.beans.Page;


/**
 * 数据库操作对象支持类
 * @author marker
 * */
public interface ISupportDao {

	public boolean save(Object entity);
	public boolean update(Object entity);
	
	
	
	
	/**
	 * 批量删除
	 * @param Class<?> clzz 有Entity注解的Bean类
	 * @param String ids 形如："1,3,5,8,2"
	 * @return boolean 处理状态
	 * */
	public boolean deleteByIds(Class<?> clzz, String ids);
	public boolean deleteByIds(Class<?> clzz, String ids, String prefix);
	public Map<String, Object> findById(Class<?> clzz, Serializable id);
	public Map<String, Object> findById(Class<?> clzz, Serializable id, String prefix);
	
	public Page findByPage(int currentPageNo, int pageSize, String sql, Object... args);
	
	public List<Map<String, Object>> queryFotList(int currentPageNo, int pageSize, String sql, Object... args);
	
	public List<Map<String, Object>> queryForList(String sql, Object... args);
	
	
	public int[] batchUpdate(String sql,List<Object[]> batchArgs);
	
	public int update(String sql, Object... args);
	
	
}
