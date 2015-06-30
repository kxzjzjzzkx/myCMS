package org.marker.mushroom.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.IChannelDao;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
 

/**
 * 栏目管理
 * @author marker
 * */
@Controller
@RequestMapping("/admin/channel")
public class ChannelController extends SupportController {
	
	@Autowired protected IChannelDao channelDao;
	
	
	/**
	 * 初始化视图路径
	 * */
	public ChannelController() {
		this.viewPath = "/admin/channel/";
	}
	
	
	
	/** 添加栏目 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request){
		request.setAttribute("channels", dao.queryForList("select * from  "+getPrefix()+"channel"));
		request.setAttribute("modules", dao.queryForList("select * from  "+getPrefix()+"module order by id asc"));
		return this.viewPath + "add";
	}
	
	//编辑栏目
	@RequestMapping("/edit")
	public String edit( HttpServletRequest request){
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("channel", dao.queryForMap("select * from  "+getPrefix()+"channel where id=?",id));
		request.setAttribute("channels", dao.queryForList("select * from  "+getPrefix()+"channel"));
		request.setAttribute("modules", dao.queryForList("select * from  "+getPrefix()+"module order by id asc"));
		return this.viewPath + "edit";
	}

	//更新栏目
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Channel channel){
		if(channelDao.update(channel)){
			return new ResultMessage(true, "更新成功!");
		}else{
			return new ResultMessage(false, "更新失败!");
		}
	}
	
	
	/** 添加栏目 */
	@ResponseBody
	@RequestMapping("/save")
	public Object save(Channel channel){
		if(commonDao.save(channel)){
			return new ResultMessage(true, "提交成功!");
		}else{
			return new ResultMessage(false, "提交失败!");
		}
	}
	
	
	
	/** 删除栏目 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") String rid){
		boolean status = commonDao.deleteByIds(Channel.class, rid);
		if(status){
			return new ResultMessage(true,"删除成功!");
		}else{
			return new ResultMessage(false,"删除失败!"); 
		}
	}
	
	
	
	/** 栏目列表 */
	@RequestMapping("/list")
	public ModelAndView list(){ 
		List<Map<String,Object>> list = commonDao.queryForList(
				"select c.*,m.name 'modulename' from  "+getPrefix()+"channel c left join  "+getPrefix()+"module m on c.module=m.type order by c.id,c.sort asc");
		ModelAndView view = new ModelAndView(this.viewPath+"list");
		view.addObject("channels", list);
		return view;
	}
	
}
