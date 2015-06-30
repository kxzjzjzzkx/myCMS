package org.marker.mushroom.controller;

import java.util.Date;
import java.util.Map;


import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.User;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.GeneratePass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



/**
 * 
 * 
 * */
@Controller
@RequestMapping("/admin/user")
public class UserController extends SupportController {

	public UserController() {
		this.viewPath = "/admin/user/";
	}
	
	/** 添加用户 */
	@RequestMapping("/add")
	public ModelAndView add(){
		ModelAndView view = new ModelAndView(this.viewPath+"add");
		return view;
	}
	
	
	/** 保存用户 */
	@ResponseBody
	@RequestMapping("/save")
	public Object save(User user){
		user.setCreatetime(new Date());//设置创建时间
		user.setPass(GeneratePass.encode(user.getPass()));//加密密码
		if(commonDao.save(user)){
			return new ResultMessage(true, "添加成功!");
		}else{
			return new ResultMessage(false,"保存失败!"); 
		}
	}
	
 
	/** 保存用户 */
	@ResponseBody
	@RequestMapping("/update")
	public Object update(User user){
		Map<String,Object> olduser = commonDao.findById(User.class, user.getId());
		String oldPass = (String) olduser.get("pass");
		if(!oldPass.equals(user.getPass())){//修改了密码
			user.setPass(GeneratePass.encode(user.getPass()));//加密密码
		}
		if(commonDao.update(user)){
			return new ResultMessage(true, "更新成功!");
		}else{
			return new ResultMessage(false,"更新失败!"); 
		}
	}
	
	
	/** 编辑用户 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") long id){
		ModelAndView view = new ModelAndView(this.viewPath+"edit");
		view.addObject("user", commonDao.findById(User.class, id));
		return view;
	}
	
	
	
	/** 删除用户 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") String rid){
		if(rid.equals("1")){//如果找到管理员
			return new ResultMessage(false,"删除失败! 该帐号为内置账户!"); 
		}
		boolean status = commonDao.deleteByIds(User.class, rid, "mr");
		if(status){
			return new ResultMessage(true,"删除成功!");
		}else{
			return new ResultMessage(false,"删除失败!"); 
		}
	}
 
	
 
	/** 显示用户列表 */
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView view = new ModelAndView(this.viewPath+"list");
		view.addObject("data", commonDao.queryForList("select * from "+getPrefix()+"user"));
		return view;
	}
	
	/** 改变用户状态 */
	@ResponseBody
	@RequestMapping("/changestatus")
	public Object changestatus(@RequestParam("id")long id, @RequestParam("status")int status){
		int re = dao.update("update  "+getPrefix()+"user set status=? where id=?", status, id);
		return re > 0? new ResultMessage(true, null):new ResultMessage(false, "更新用户状态失败!");
	}
}
