package org.marker.mushroom.beans;

import org.marker.mushroom.core.SystemStatic;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.module.IModuleFactory;
import org.marker.mushroom.sql.Sql;



/**
 * 通过模板引擎解析得到的数据对象
 * @author marker
 * */
public final class SQLDataEngine {

	//数据库表名称
	private String tableName;
	//request传递的集合名称
	private String items;
	//临时对象名称
	private String var;
	//条件
	private String where = "";
	//查询第几页数据
	private int page = 1;
	//限制条数
	private int limit = 10;
	//排序
	private String order; //例如: "id desc"
	
	//通过doinfo方法生成的SQL语句
	private String queryString;
	
	//因为这个是在程序启动后，才有生成这类对象
	private IModuleFactory moduleFactory = SpringContextHolder.getBean(SystemStatic.SYSTEM_MODULE_FACTORY);
	
	
	 
	
	/**
	 * 生成Sql语句
	 * */
	public void generateSql(){
		String prefix = DataBaseConfig.getInstance().get("mushroom.db.prefix");//表前缀，如："yl_"
		
		StringBuilder queryString = new StringBuilder();
		queryString.append(Sql.QUERY_FOR_FORM).append(prefix)
				.append(this.tableName).append(Sql.QUERY_FOR_ALIAS);


		
		//如果是模型中的表，那么就到模型工厂里面取得前部分sql语句
		StringBuilder temp = moduleFactory.parse(this.tableName, this);
		if(temp != null){ 
			queryString = temp;
		} 
  
		
		//追加条件语句
		String where = this.where; 
		if (where != null && !"".equals(where)) {// 如果条件语句存在
			queryString.append(Sql.QUERY_FOR_WHERE);
			String[] ws = where.split(",");
			for (int i = 0; i < ws.length; i++) {
				queryString.append(Sql.QUERY_FOR_ALIAS_DOT + ws[i]);
				if (i != (ws.length-1)) {
					queryString.append(Sql.QUERY_FOR_AND);
				}
			}
		}
		
		
		
		//追加排序语句
		if (this.order != null && !"".equals(this.order)) {
			queryString.append(Sql.QUERY_FOR_ORDERBY)
					.append(Sql.QUERY_FOR_ALIAS_DOT).append(this.order);
		}

		this.queryString = queryString.toString();
	}
	
	
	
	
	
	public String getQueryString() {
		return queryString;
	}





	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}





	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
 
	
	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public String toString(){
		String a = "Table: "+this.tableName;
		 a += "\n items:"+this.items;
		 a += "\n var:"+this.var;
		 a += "\n limit:"+this.limit; 
		 a += "\n where:"+this.where;
		 a += "\n order:"+this.order;
		 a += "\n page:"+page;
		return a;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

 
	
	
	
	
}
