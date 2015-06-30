package org.marker.mushroom.module.impl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.beans.Article;
import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.core.AppResolving;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.module.CommonModule;
import org.marker.mushroom.sql.Sql;
import org.marker.urlrewrite.URLRewrite;

/**
 * 文章模型用来处理文章信息的
 * 
 * @author marker
 * */
public class ArticleModule extends CommonModule {

	public ArticleModule() {
		this.commonDao = SpringContextHolder.getBean("commonDao");
	}
	
	public void doGet(HttpServletRequest request, ServletContext application, Channel currentChannel,
			AppResolving resv) {
		String prefix = DataBaseConfig.getInstance().get("mushroom.db.prefix");//表前缀，如："yl_"
		
		
		long pid  = currentChannel.getId();//当前栏目ID
		int limit = currentChannel.getRows();//每页内容条数
 
		
		int pageNo = 1;
		if(resv.page != null && !"".equals(resv.page)){
			try{
				pageNo = Integer.parseInt(resv.page);
			}catch (Exception e) {e.printStackTrace(); }
		}

		
		
		StringBuilder sql = new StringBuilder();
		sql.append("select A.id,A.title,C.name as cname ,A.time ,concat('/cms?p=',C.url,'&type=article&id=',CAST(A.id as char),'&time=',DATE_FORMAT(A.time,'%Y%m%d')) as url from ");
		sql.append(prefix).append("article").append(Sql.QUERY_FOR_ALIAS)
		 .append(" join ").append(prefix).append("channel").append(" C on A.pid=C.id");
		sql.append(" where A.pid=").append(pid);
		
	 
		Page currentPage = commonDao.findByPage(pageNo, limit, sql.toString());
		
		request.setAttribute(AppStatic.WEB_APP_PAGE, currentPage);
		
		//传递分页信息
		String nextPage = "/cms?p="+resv.pageName+"&page="+currentPage.getNextPageNo();
		String prevPage = "/cms?p="+resv.pageName+"&page="+currentPage.getPrevPageNo();
		request.setAttribute("nextpage", URLRewrite.me().encoder(nextPage));
		request.setAttribute("prevpage", URLRewrite.me().encoder(prevPage));
		
	}
	
	
	
	/**
	 * 前台标签生成SQL遇到该模型则调用模型内算法
	 * @param tableName 表名称
	 * */
	public StringBuilder doWebFront(String tableName, SQLDataEngine obj) {
		String prefix = DataBaseConfig.getInstance().get("mushroom.db.prefix");// 表前缀，如："yl_"
		StringBuilder sql = new StringBuilder("select ");
		sql.append("A.id,A.title,C.name as 'cname' ,A.time ,concat('/cms?p=',C.url,'&type=',C.module,'&id=',CAST(A.id as char),'&time=',DATE_FORMAT(A.time,'%Y%m%d')) as 'url' from ");
		sql.append(prefix).append("article").append(Sql.QUERY_FOR_ALIAS)
				.append(" join ").append(prefix).append("channel").append(" C ")
				.append("on A.pid=C.id");
		return sql;
	}



 
	public void doContent(HttpServletRequest request,
			ServletContext application, Channel currentChannel,
			AppResolving resv) {
		String prefix = DataBaseConfig.getInstance().get("mushroom.db.prefix");// 表前缀，如："yl_"
		long cid = Long.parseLong(resv.contentId);// 获取指定ID的文章信息
		Object article = commonDao.findById(Article.class, cid, prefix);
		commonDao.update("update " + prefix
				+ "article set views = views+1 where id=?", cid);// 更新浏览量
		request.setAttribute("article", article);
		resv.template = this.template;// 模型的模板
	}



	

}
