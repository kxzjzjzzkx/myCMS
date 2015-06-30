package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Chip;
import org.marker.mushroom.beans.Module;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 内容模型管理
 * @author marker
 * */
@Controller
@RequestMapping("/admin/module")
public class ModuleController extends SupportController {

	@Autowired private ISupportDao commonDao;
	
	
	public ModuleController() {
		this.viewPath = "/admin/module/";
	}
	
	//添加模型
	@RequestMapping("/add")
	public String add(){
		return this.viewPath + "add";
	}
	
	
	
	/** 编辑用户 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") long id){
		ModelAndView view = new ModelAndView(this.viewPath+"edit");
		view.addObject("chip", commonDao.findById(Chip.class, id));
		return view;
	}
	
	
	/** 保存 */
	@ResponseBody
	@RequestMapping("/save")
	public Object save(Module module){
		if(commonDao.save(module)){
			return new ResultMessage(true, "安装模型成功");
		}else{
			return new ResultMessage(false, "安装模型失败"); 
		}
	}
	
	
	
	/** 更新模型 */
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Module module){
		if(commonDao.update(module)){
			return new ResultMessage(true, "更新模型成功");
		}else{
			return new ResultMessage(false,"更新模型失败!"); 
		}
	}
	
	
	//删除
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") String rid){
		boolean status = commonDao.deleteByIds(Module.class, rid, "mr");
		if(status){
			return new ResultMessage(true,"删除成功!");
		}else{
			return new ResultMessage(false,"删除失败!"); 
		}
	}
	
	
	
	//显示列表
	@RequestMapping("/list")
	public ModelAndView list(Page page){
		ModelAndView view = new ModelAndView(this.viewPath+"list");
		view.addObject("page", commonDao.findByPage(page.getCurrentPageNo(), 15, "select * from  "+getPrefix()+"module"));
		return view;
	}
	
 
}
