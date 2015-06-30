package org.marker.mushroom.plugins;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



/**
 * 插件文件处理类
 * 主要是读取插件配置信息，还有插件内容，内库解压操作
 * @author marker
 * @date 2013-01-19
 * */
public class PluginZipFile {

	//ZIPFile对象
	private ZipFile zipFile;
	
	//插件压缩包中配置文件名称
	private static String PLUGIN_CONFIG_NAME = "plugin.config";
	
	private String PluginPath = "";
	
	private String libPath    = "";
	
	public PluginZipFile(String zipPath) throws IOException{
		this.zipFile = new ZipFile(new File(zipPath));
	}
	
	
	public PluginZipFile(File zipPath) throws IOException{
		this.zipFile = new ZipFile(zipPath);
	}
	
	
	/**
	 * 读取配置信息
	 * @return Map<String, String> 配置信息
	 * @throws IOException 
	 * */
	public Map<String, String> getCongfig() {
		Map<String, String> config = new HashMap<String, String>();
		InputStream In        = null ;
		InputStreamReader isr = null ;
		try{
			In  = zipFile.getInputStream(zipFile.getEntry(PLUGIN_CONFIG_NAME));
			isr = new InputStreamReader(In,"UTF-8");//防止乱码
			Properties pro = new Properties();
			pro.load(isr);
			for(Object set : pro.keySet()){
				config.put(set.toString(), pro.getProperty(set.toString()));
			}
		}catch (Exception e) {
			System.out.println("读取插件配置异常");
		}finally{ 
			try {
				isr.close(); In.close();
			} catch (IOException e) { 
				e.printStackTrace();
			} 
		}
		return config;
	}
	
	
	public void export(){
		try {
			Enumeration<?> entries = zipFile.entries();
			ZipEntry entry = null;
			while (entries.hasMoreElements()) {
				entry = (ZipEntry) entries.nextElement();
				if(PLUGIN_CONFIG_NAME.equals(entry.getName())){//这是配置文件，不做导出处理
					continue;
				}else if(entry.getName().endsWith("/")){//
					continue;
				}
				String entryName = entry.getName();
				if(entryName.startsWith("lib")){//这是内库，应该存放到指定lib目录里
					entryName = entryName.substring(entryName.lastIndexOf("/")+1, entryName.length());
					this.writeFileByZipEntry(entry, libPath + entryName);
				}else{//这是普通文件
					this.writeFileByZipEntry(entry, PluginPath + entryName);
				}
			} 
		} catch ( Exception e) { 
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 写入文件
	 * 
	 * */
	private void writeFileByZipEntry(ZipEntry entry,String path) throws IOException{
		InputStream  In = zipFile.getInputStream(entry);
		File file = new File(path);
		//如果该文件夹不存在就创建
	    if(!file.getParentFile().exists() )
	    	file.getParentFile().mkdirs();
		OutputStream ow = new FileOutputStream(file, true);
		byte[] b = new byte[256];
		int len = In.read(b);
		while (len > 0) {
			ow.write(b, 0, len);
			len = In.read(b);
		}
		ow.flush();
		ow.close();
		In.close(); 
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		PluginZipFile a = new PluginZipFile("temp/guestbook.zip");
		
		Map<String,String> cfg = a.getCongfig();
		System.out.println(cfg.get("plugin.name"));
		
		a.export();//导出数据
	}


	public String getPluginPath() {
		return PluginPath;
	}


	public void setPluginPath(String pluginPath) {
		PluginPath = pluginPath;
	}


	public String getLibPath() {
		return libPath;
	}


	public void setLibPath(String libPath) {
		this.libPath = libPath;
	}


 
	
	
	
}
