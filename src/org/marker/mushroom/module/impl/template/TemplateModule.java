package org.marker.mushroom.module.impl.template;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.core.AppResolving;
import org.marker.mushroom.core.channel.ChannelItem;
import org.marker.mushroom.core.channel.ChannelTree;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.dao.IChannelDao;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.module.CommonModule;
import org.marker.urlrewrite.URLRewrite;



public class TemplateModule extends CommonModule {
 
	public TemplateModule() {
		this.commonDao = SpringContextHolder.getBean("commonDao");
		this.dao = SpringContextHolder.getBean("jdbcTemplate");
	}
	
	public void doGet(HttpServletRequest request, ServletContext application, Channel currentChannel,
			AppResolving resv) { 
		//分页查询模板 
		long pid  = currentChannel.getId();//当前栏目ID
		int limit = currentChannel.getRows();//每页内容条数
 
		
		
		int pageNo = 1;
		if(resv.page != null && !"".equals(resv.page)){
			try{
				pageNo = Integer.parseInt(resv.page);
			}catch (Exception e) {e.printStackTrace(); }
		}
		
		
		
		
		IChannelDao channelDao = SpringContextHolder.getBean("channelDao");
		
		List<Channel> list = channelDao.findAll();
		
		ChannelItem currentItem = ChannelTree.foreach(pid, currentChannel, list);
		
		
		String str = currentItem.getChildIdToString();
 
		String queryString = "select A.id,A.name,A.name as cname ,A.time ,A.durl,A.dloaded,a.grade,A.author,A.icon,A.description,A.views,concat('/cms?p=',c.url,'&type=template&id=',CAST(A.id as char),'&time=',DATE_FORMAT(A.time,'%Y%m%d')) as url from mr_template A" +
				" join mr_channel c on a.pid=c.id " +
				" where A.pid in("+str+")";
	 
		Page currentPage = commonDao.findByPage(pageNo, limit, queryString);
		request.setAttribute("page", currentPage);
		
		//传递分页信息
		String nextPage = "/cms?p="+resv.pageName+"&page="+currentPage.getNextPageNo();
		String prevPage = "/cms?p="+resv.pageName+"&page="+currentPage.getPrevPageNo();
		request.setAttribute("nextpage", URLRewrite.me().encoder(nextPage));
		request.setAttribute("prevpage", URLRewrite.me().encoder(prevPage));
			
  
	}
 
	
	
	
	public StringBuilder doWebFront(String tableName,
			SQLDataEngine obj) {
		StringBuilder queryString = new StringBuilder(
				"select a.id,a.name,a.icon,a.durl,a.grade,a.dloaded,a.author,c.name as cname ,a.time ,concat('cms?p=',c.url,'&type=template&id=',CAST(a.id as char),'&time=',DATE_FORMAT(a.time,'%Y%m%d')) as url from template a,channel c ");
		if(obj.getWhere() != null && !"".endsWith(obj.getWhere())){
			queryString.append("where ");
			String[] ws = obj.getWhere().split(",");
			int count = 0;
			for(String a : ws){
				count++;
				queryString.append("a."+a);
				if(count != ws.length){
					queryString.append(" and ");
				}
			}
			queryString.append(" and a.pid=c.id ");
		}else{
			queryString.append(" where a.pid=c.id "); 
		} 
		return queryString;
	}

	
	/**
	 * 根据参数ID获取内容信息，并更新views值 
	 * */ 
	public void doContent(HttpServletRequest request,
			ServletContext application, Channel currentChannel, AppResolving resv) {
		String prefix = DataBaseConfig.getInstance().get("mushroom.db.prefix");// 表前缀，如："yl_"
		long cid = Long.parseLong(resv.contentId);// 内容ID 
		Object tpl = commonDao.findById(Template.class, cid, prefix); 
		commonDao.update("update mr_template set views = views+1 where id=?", cid);// 更新浏览量
		request.setAttribute("template", tpl);
		resv.template = this.template;// 模型的模板
	}

}
