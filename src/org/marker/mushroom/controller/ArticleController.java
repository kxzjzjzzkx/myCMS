package org.marker.mushroom.controller;

import java.util.Date;

import org.marker.mushroom.beans.Article;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文章管理
 * @author marker
 * */
@Controller
@RequestMapping("/admin/article")
public class ArticleController extends SupportController {

	
	public ArticleController() {
		this.viewPath = "/admin/article/";
		
	}
	
	//发布文章
	@RequestMapping("/publish")
	public ModelAndView publish(){
		ModelAndView view = new ModelAndView(this.viewPath+"publish");
		view.addObject("channels", commonDao.queryForList("select * from  "+getPrefix()+"channel where module in ('article','help')"));
		return view;
	}
	
	//编辑文章
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") long id){
		ModelAndView view = new ModelAndView(this.viewPath+"edit");
		view.addObject("article", commonDao.findById(Article.class, id));
		view.addObject("channels", commonDao.queryForList("select * from  "+getPrefix()+"channel where module in ('article','help')"));
		return view;
	}
	
	
	//保存
	@ResponseBody
	@RequestMapping("/save")
	public Object save(Article article){
		article.setTime(new Date());
		if(commonDao.save(article)){
			return new ResultMessage(true, "发布成功!");
		}else{
			return new ResultMessage(false,"发布失败!"); 
		}
	}
	
	
	//保存
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Article article ){
		article.setTime(new Date());
		if(commonDao.update(article)){
			return new ResultMessage(true, "更新成功!");
		}else{
			return new ResultMessage(false,"更新失败!"); 
		}
	}
	
	
	
	//删除文章
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") String rid){
		boolean status = commonDao.deleteByIds(Article.class, rid);
		if(status){
			return new ResultMessage(true,"删除成功!");
		}else{
			return new ResultMessage(false,"删除失败!"); 
		}
	}
 
	
	/** 文章列表  */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam("currentPageNo") int currentPageNo){
		ModelAndView view = new ModelAndView(this.viewPath+"list");
		view.addObject("page", commonDao.findByPage(currentPageNo, 15, 
				"select a.*,c.name as cname from  "+getPrefix()+"article as a left join  "+getPrefix()+"channel c on c.id=a.pid order by a.id desc"));
		return view;
	}
	
}
