<%@page import="org.marker.mushroom.security.DES"%>
<%@page import="java.io.File"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
//如果已经安装了本系统，那么就转向主页
if(new File("../data/install.lock").exists()){ response.setHeader("locaion", "../"); }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>第二步、配置软件信息</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script src="../public/js/jquery.js"></script>
</head>

<body>
	<form action="progress.jsp" method="post" name="form" id="form">
	<div id="install" class="data">
	  <h1>第二步、配置软件信息</h1>
	  <div class="content">
	    <div class="list">
	      <div class="name">数据库地址：</div>
	      <div class="value"><input type="text" class="input" name="DB_HOST" id="DB_HOST" value="localhost" onblur="test_data()" /></div>
	    </div>
	    <div class="list">
	      <div class="name">数据库端口：</div>
	      <div class="value"><input type="text" class="input" name="DB_PORT" id="DB_PORT" value="3306" onblur="test_data()" /></div>
	    </div>
	    <div class="list">
	      <div class="name">数据库名称：</div>
	      <div class="value"><input type="text" class="input" name="DB_NAME" id="DB_NAME" value="db_ylcms"  onblur="test_data()" /> </div>
	      <div class="tip"></div>
	    </div>
	    <div class="list">
	      <div class="name">数据库用户名：</div>
	      <div class="value"><input type="text" class="input" name="DB_USER" id="DB_USER" value="root"  onblur="test_data()" /></div>
	    </div>
	    <div class="list">
	      <div class="name">数据库密码：</div>
	      <div class="value"><input type="text" class="input" name="DB_PWD" id="DB_PWD" value="" onblur="test_data()" /></div>
	    </div>
	    <div class="list">
	    <div class="name">数据库状态：</div><div class="msg" style="float:left;"><span style="color:#666">等待检测中...</span></div>
	    </div>
	   
	    <div class="list">
	      <div class="name">数据表前缀：</div>
	      <div class="value"><input type="text" class="input" name="DB_PREFIX"  value="yl_" id="DB_PREFIX" /></div>
	    </div>  
	    <div class="list">
	      <div class="name">安全加密码：</div>
	      <div class="value"><input type="text" class="input" name="spot" value="<%=DES.getSecretKey(null) %>" /></div>
	    </div>
	  </div>
	  <div class="menu">
	  	<input name="test_conn" id="test_conn" type="hidden" value="0" />
	    <button type="button" class="submit" onclick="ins_form();" >准备完毕进入安装</button>
	  </div>
	</div>
	</form>
</body>
</html>
 
<script>
function ins_form(){
	test_data();
	if($('#test_conn').val()==0){
		return false();
	}else{
		$("form").submit();
	}
}

function test_data(){
	 val=$('#create').attr("checked");
     if(val){
		 create=1;
     }else{
     	create=0;
     } 
	 $.post( 'data.jsp?action=test_data',
	 {
		 DB_HOST:$('#DB_HOST').val(),
		 DB_PORT:$('#DB_PORT').val(),
		 DB_NAME:$('#DB_NAME').val(),
		 DB_USER:$('#DB_USER').val(),
		 DB_PWD:$('#DB_PWD').val(),
		 create:create
	 }, function(html){
		 if(html==1){
			 $('.msg').html('<span style="color:green">数据库连接成功</span>');
			 $('#test_conn').val('1');
		 }else{
			 $('.msg').html('<span style="color:red">'+html+'</span>');
			 $('#test_conn').val('0');
		 }
	 },'html');
}

</script>
 