package org.marker.mushroom.module;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.Module;
import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.AppResolving;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.SystemStatic;
import org.marker.mushroom.dao.IChannelDao;
import org.marker.mushroom.dao.IModuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 模型工厂
 * 好处在于我可以做很多模型来显示特定的信息，如：文章、商品、下载等信息他们都是不同模型驱动的，这样划分出来。
 * 
 * 
 * 处理是通过请求中的type来选择调用相应的模型
 * @author marker
 * 
 * */
@Component(SystemStatic.SYSTEM_MODULE_FACTORY)
public class ModuleFactory implements IModuleFactory{
	
	private static final Log log = LogFactory.getLog(ModuleFactory.class);
	
	//自动注入应用作用域
	@Autowired private ServletContext application;
	 
	
	/** 内容模型Dao */
	@Autowired private IModuleDao moduleDao;
	@Autowired private IChannelDao channelDao;
	private boolean isInit = false;
	 
	
	
	/** 存放模型的集合(key:类型 value:模型对象) */
	private static final Map<String,CommonModule> modules = new ConcurrentHashMap<String,CommonModule>();
	
 
	/**
	 * 通过解析对象找到对应的模型
	 * */
	public void parse(AppResolving resv) {
		isinit();
		HttpServletRequest request = ActionContext.getReq();
		
		//查询当前访问的栏目信息，栏目信息里面包含模型调用对应的模型库
		Channel currentChannel = channelDao.queryByUrl(resv.pageName);
		if(currentChannel != null){
			request.setAttribute(AppStatic.WEB_CURRENT_CHANNEL, currentChannel);
			resv.template = currentChannel.getTemplate();//模板
			resv.pageType = currentChannel.getModule();//内容模型
			
			//查到栏目对应的模型，然后进行相应操作
			IModule mod = modules.get(resv.pageType);//获取模型
			if(mod != null){
				//获取应用作用域
				if(resv.contentId != null && !"".equals(resv.contentId)){
					mod.doContent(request,application,currentChannel,resv);
				}else{
					mod.doGet(request,application,currentChannel,resv);
				}
			}
		}
	}

	
	//初始化模型对象在监听器中调用
	public void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		//初始化插件模型
		if(!isInit){
			List<Module> list = moduleDao.queryAll();
			for(Module mod : list){
				CommonModule commonModule = createModule(mod.getUri());
				commonModule.name = mod.getName();
				commonModule.type = mod.getType();
				commonModule.template = mod.getTemplate();
				modules.put(commonModule.type, commonModule);//添加
			}
			isInit = true;
		}
	}
	
	private static CommonModule createModule(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		if(className != null && !"".equals(className)){//如果类路径不为空
			@SuppressWarnings("unchecked")
			Class<CommonModule> clzz = (Class<CommonModule>) Class.forName(className);
			return clzz.newInstance();
		}
		return null;
	}




	public StringBuilder parse(String tableName, SQLDataEngine sqlDataEngine) {
		isinit();
		//当type=null的时候应该获取栏目的模型，然后进行处理
		IModule mod = modules.get(tableName);//获取模型
		if(mod != null){
			return mod.doWebFront(tableName, sqlDataEngine);
		}
		return null; 
	}


 
	//懒加载模式
	private synchronized void isinit(){
		if(!isInit){ 
			try {
				init();
			} catch (ClassNotFoundException e) {
				log.error("Class Not Found!", e);
			} catch (InstantiationException e) { 
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} 
			 
		}
	}
}
