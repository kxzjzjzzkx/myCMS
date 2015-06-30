package org.marker.mushroom.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.marker.mushroom.beans.FileUploadBean;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.Plugin;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.holder.WebRealPathHolder;
import org.marker.mushroom.plugins.PluginZipFile;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;




/**
 * 插件控制器
 * @author marker
 * */
@Controller
@RequestMapping("/admin/plugin")
public class PluginController extends SupportController {

	public PluginController() {
		this.viewPath = "/admin/plugin/";
	}
	
 
	//添加插件
	@RequestMapping("/install")
	public String install(){ 
		return this.viewPath + "install";
	}
	
	
	//保存插件
	@ResponseBody
	@RequestMapping("/save")
	public Object save(HttpServletResponse response, FileUploadBean uploadItem) throws IOException{
	 
		String realPath = WebRealPathHolder.REAL_PATH;
		String saveFilePath = null;
		if (uploadItem != null && uploadItem.getFile()!= null && uploadItem.getFile().getSize() > 0) {
			CommonsMultipartFile cmf = uploadItem.getFile(); 
			saveFilePath = realPath + File.separator + "temp" + File.separator + cmf.getOriginalFilename();
			InputStream is = cmf.getInputStream();
			OutputStream os = new FileOutputStream(saveFilePath);
			int tempByte;
			while ((tempByte = is.read()) != -1) {
				os.write(tempByte);
			}
			os.flush();
			os.close();
			is.close();

			
			PluginZipFile a = null;
			try {
				a = new PluginZipFile(saveFilePath);
				//设置插件目录
				a.setPluginPath(realPath+"plugins/");
				//设置内库目录
				a.setLibPath(realPath+"WEB-INF/libs/"); 
				Map<String,String> cfg = a.getCongfig(); //获取插件配置信息
				
				Plugin plugin = new Plugin();
				plugin.setName(cfg.get("plugin.name"));//插件名称
				plugin.setMark(cfg.get("plugin.mark"));//插件标签
				plugin.setStatus((short)1);
				plugin.setUri(cfg.get("plugin.uri"));//设置实现类
				plugin.setAuthor(cfg.get("plugin.author"));//设置作者
				plugin.setVer(cfg.get("plugin.ver"));//设置版本
				System.out.println(cfg.get("plugin.name"));
				a.export();//导出数据 
				commonDao.save(plugin);
			} catch (IOException e) { 
				e.printStackTrace();
			} 
			return new ResultMessage(true, "安装成功!");
		}
		return new ResultMessage(false, "安装失败!") ;
	}
	
	
	
	/**
	 * 删除用户
	 * @param rid
	 * @return json
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") String rid){
		boolean status = commonDao.deleteByIds(Plugin.class, rid, "mr");
		if(status){
			return new ResultMessage(true,"删除成功!");
		}else{
			return new ResultMessage(false,"删除失败!"); 
		}
	}
	
	 
	//显示插件列表
	@RequestMapping("/list")
	public ModelAndView list(Page page){
		ModelAndView view = new ModelAndView(this.viewPath+"list");
		view.addObject("page", commonDao.findByPage(page.getCurrentPageNo(), 15, "select * from  "+getPrefix()+"plugin"));
		return view;
	}

	//获取插件二级菜单
//	public String pluginmenu(){
//		YLPluginFactory factory = YLPluginFactory.getInstance();
//		String menu = factory.getMenu();
//		put("data", menu);//写入数据
//		return "dispatcherData";
//	}
	
	
//	//后台主页
//	public void action(){
//		String pluginName = getParameter("pname");
//		String method = getParameter("method");
//		if(pluginName != null && !"".equals(pluginName)){
//			
//			YLPluginFactory f = YLPluginFactory.getInstance();
//			PluginMode mode =  f.get(pluginName);
//			if(mode != null){
//				//处理提交数据
//				mode.initialize(request, response, application);//初始化请求、响应对象
//				
//				mode.invoke(method);
//				
//			}
//			
//		}
//	} 
}
