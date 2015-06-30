package org.marker.mushroom.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.an.Entity;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractCommonDao implements ISupportDao {

	/** 自动注入jdbc模板操作 */
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	/** 表名称 */
	protected String tableName;
	/** 主键 */
	protected String primaryKey;

	/** 数据库配置 */
	protected DataBaseConfig dbConfig = DataBaseConfig.getInstance();

	public AbstractCommonDao() { }

	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		return jdbcTemplate.queryForList(sql, args);
	}

	public int[] batchUpdate(String sql, List<Object[]> batchArgs) {

		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	public int update(String sql, Object... args) {
		return jdbcTemplate.update(sql, args);
	}

	public boolean deleteByIds(Class<?> clzz, String ids) {
		return deleteByIds(clzz, ids, "");
	}

	public boolean deleteByIds(Class<?> clzz, String ids, String prefix) {
		String tableName = clzz.getAnnotation(Entity.class).value();
		String primaryKey = clzz.getAnnotation(Entity.class).key();
		int status = jdbcTemplate.update("delete from " + prefix + tableName
				+ " where " + primaryKey + " in(" + ids + ")");
		return status > 0 ? true : false;
	}

	public Map<String, Object> findById(Class<?> clzz, Serializable id) {
		String tableName = clzz.getAnnotation(Entity.class).value();
		String primaryKey = clzz.getAnnotation(Entity.class).key();
		return queryForMapNoException("select * from " + tableName + " where "
				+ primaryKey + "=?", id);
	}

	public Map<String, Object> findById(Class<?> clzz, Serializable id,
			String prefix) {
		String tableName = clzz.getAnnotation(Entity.class).value();
		String primaryKey = clzz.getAnnotation(Entity.class).key();
		return queryForMapNoException("select * from " + prefix + tableName
				+ " where " + primaryKey + "=?", id);
	}

	private Map<String, Object> queryForMapNoException(String sql,
			Object... args) {
		try {
			return jdbcTemplate.queryForMap(sql, args);
		} catch (Exception e) {
		}
		return new HashMap<String, Object>();
	}

	/**
	 * 
	 * 
	 * */
	public Page findByPage(int currentPageNo, int pageSize, String sql,
			Object... args) {

		int beginPos = sql.toLowerCase().indexOf("from");
		String hql4Count = "select count(*) " + sql.substring(beginPos);
		sql += " limit " + (currentPageNo - 1) * pageSize + "," + pageSize;

		// 获取总条数
		@SuppressWarnings("deprecation")
		long count = jdbcTemplate.queryForLong(hql4Count, args);
		;// 获取条数

		List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, args);
		Page page = new Page();
		page.setData(data);// 获取数据集合
		page.setCurrentPageNo(currentPageNo);
		page.setPageSize(pageSize);
		page.setTotalRows(count);// 设置总行数
		return page;

	}

	public boolean save(Object entity) {
		Class<?> clzz = entity.getClass();
		Entity tableInfo = clzz.getAnnotation(Entity.class);
		String tableName = tableInfo.value();
		String primaryKey = tableInfo.key();
		String prefix = dbConfig.getPrefix();

		// 构造SQL字符串
		StringBuilder sql = new StringBuilder("insert into ").append(prefix)
				.append(tableName).append("(");
		StringBuilder val = new StringBuilder(" values(");
		Field[] fields = clzz.getDeclaredFields();
		int length = fields.length;
		List<Object> list = new ArrayList<Object>(length);
		for (int i = 0; i < length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			if (fieldName.equals(primaryKey)) {// 如果是主键
				continue;
			}
			if ("serialVersionUID".equals(fieldName)) {
				continue;
			}
			String methodName = "get"
					+ fieldName.replaceFirst(fieldName.charAt(0) + "",
							(char) (fieldName.charAt(0) - 32) + "");

			Method me = null;
			try {
				me = clzz.getMethod(methodName);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			Object returnObject = null;
			try {
				returnObject = me.invoke(entity);
			} catch (IllegalArgumentException e) { 
				e.printStackTrace();
			} catch (IllegalAccessException e) { 
				e.printStackTrace();
			} catch (InvocationTargetException e) { 
				e.printStackTrace();
			}//
			if (returnObject != null) {// 如果返回值为null
				sql.append("`" + fieldName + "`");
				val.append("?");
				list.add(returnObject);
				if (i < (length - 1)) {
					sql.append(", ");
					val.append(", ");
				}
			}
		}
		sql.append(")").append(val).append(")");
		System.out.println(sql);
		int status = update(sql.toString(), list.toArray());
		return status > 0 ? true : false;
	}

	public boolean update(Object entity) {
		Class<?> clzz = entity.getClass();
		Entity tableInfo = clzz.getAnnotation(Entity.class);
		String tableName = tableInfo.value();
		String primaryKey = tableInfo.key();
		String prefix = dbConfig.getPrefix();

		StringBuffer sql = new StringBuffer();
		sql.append("update `").append(prefix).append(tableName)
				.append("` set ");
		List<Object> list = null;
		long id = 0;
		try {
			id = (Long) clzz.getMethod("getId").invoke(entity);
			Field[] fields = clzz.getDeclaredFields();
			int length = fields.length;
			list = new ArrayList<Object>(length);
			for (int i = 0; i < length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if (fieldName.equals(primaryKey)) {// 如果是主键
					continue;
				}
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				String methodName = "get"
						+ fieldName.replaceFirst(fieldName.charAt(0) + "",
								(char) (fieldName.charAt(0) - 32) + "");
				Method me = clzz.getMethod(methodName);
				Object returnObject = me.invoke(entity);
				if (returnObject != null) {// 如果返回值为null
					sql.append("`" + fieldName + "`").append("=?");
					list.add(returnObject);
					if (i < (length - 1)) {
						sql.append(", ");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		list.add(id);
		sql.append(" where `").append(primaryKey).append("`=?");

		int status = update(sql.toString(), list.toArray());
		return status > 0 ? true : false;
	}

	public List<Map<String, Object>> queryFotList(int currentPageNo,
			int pageSize, String sql, Object... args) {
		sql += " limit " + (currentPageNo - 1) * pageSize + "," + pageSize;
		return jdbcTemplate.queryForList(sql, args);
	}

}
