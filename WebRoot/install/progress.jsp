<%@page import="org.marker.mushroom.utils.GeneratePass"%>
<%@page import="org.marker.mushroom.core.config.impl.SystemConfig"%>
<%@page import="org.marker.mushroom.core.AppStatic"%>
<%@page import="org.marker.mushroom.core.config.impl.DataBaseConfig"%>
<%@page import="java.io.FileReader"%>
<%@page import="com.mchange.v2.c3p0.ComboPooledDataSource"%>
<%@page import="org.marker.mushroom.holder.SpringContextHolder"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%> 
<%@page import="org.marker.mushroom.utils.FileTools"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.OutputStream"%> 
<%@page import="java.io.File"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>安装状态</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>




<%
//如果已经安装了本系统，那么就转向主页
if(new File("../data/install.lock").exists()){ response.setHeader("locaion", "../"); }

String path     = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String host = request.getParameter("DB_HOST");
String demo = request.getParameter("DB_NAME");
String port = request.getParameter("DB_PORT");
String user = request.getParameter("DB_USER");
String password = request.getParameter("DB_PWD");
String spot = request.getParameter("spot");//加密Key
String prefix = request.getParameter("DB_PREFIX");//表前缀

String jdbcurl = "jdbc:mysql://"+host+":"+port+"/"+demo+"?useUnicode=true&characterEncoding=UTF-8";
 
 
//数据库设置
ComboPooledDataSource cmpd = SpringContextHolder.getBean("dataSource");
cmpd.setJdbcUrl(jdbcurl);//设置
cmpd.setUser(user);
cmpd.setPassword(password);
DataBaseConfig dbc = DataBaseConfig.getInstance();
dbc.put("mushroom.db.host", host);
dbc.put("mushroom.db.port", port);
dbc.put("mushroom.db.demo", demo);
dbc.put("mushroom.db.user", user); 
dbc.put("mushroom.db.pass", password);
dbc.put("mushroom.db.prefix", prefix);
dbc.store();//保存
 

//系统配置
SystemConfig sysconfig = SystemConfig.getInstance();
sysconfig.put("secret_key", spot);//更新Key
sysconfig.store();//保存





Connection conn = null;

try{
	conn = cmpd.getConnection();
	
	//如果数据库不存在就创建该数据库
	PreparedStatement psa = conn.prepareStatement("CREATE database IF NOT EXISTS " + demo);
	psa.executeUpdate();
	psa.close();
	 
	
	//创建表
	String basePath2 = application.getRealPath("/");
	File sqlFile = new File(basePath2+"/data/sql/database.sql");
	String sql = FileTools.getFileContet(sqlFile, "utf-8");
	String[] sqla = sql.split(";");
	conn.setCatalog(demo);
	Statement ps = conn.createStatement(); 
	for(String a : sqla){ 
		if(a != null && !"".equals(a.trim())){
			ps.addBatch(a);
		}
	}
	ps.executeBatch();
	ps.close();
	
	
	
	
		
	//更新密码
	String pass = GeneratePass.encode("admin");
	psa = conn.prepareStatement("update user set pass='"+pass+"' where id=1");
	psa.executeUpdate();
	psa.close();
%>

    安装完成....
    
    <a href="../">进入</a>
    <br>
    
<%
    //设置安装状态
    application.setAttribute(AppStatic.WEB_APP_INSTALL, true);
    String BasePath = application.getRealPath("/data/");
   	OutputStream os = new FileOutputStream(new File(BasePath+"/install.lock"));
   	os.write(1);
   	os.flush();
   	os.close();

}catch(Exception e){
	out.print("安装失败!");
	e.printStackTrace();
}finally{

	
	conn.close();
}
%> 
  </body>
</html>
