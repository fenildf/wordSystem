<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getContextPath();
    request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="${basePath}/static/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}/static/layer/layer.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/jquery-form.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
</head>
<body style="width:100%;height:100%;text-align: center">
   
   <div style="margin-top:100px">
    <form id="login_form">
       <input type="text" id="loginName" name="loginName" placeholder="请输入账号" style="height:20px"/></br>
       <input type="password" id="password" name="password" placeholder="请输入密码" style="margin-top:5px;height:20px"/></br>
       <input id="submitBtn" type="button" value="登录" style="margin-top:10px"/>
    </form>
   </div>
   
  <script type="text/javascript">
 
  	
  $("#submitBtn").bind("click",login);
  
  function login(){
	  $("submitBtn").unbind("click",login);
	  //请求后台数据
	  var appData={    
	  	  url:'${basePath}/person/login.do',
	  	  resetForm: false, 
	  	  type:'POST',
	  	  dataType : 'json',
	  	  beforeSubmit: function(){
	  	       return checkParams();//这里可以自定义参数检查器
	  	   },
	  	  success:function(result){
	  		$("#submitBtn").bind("click",login);
	  	      if(result.code==200){
	  	    	  window.location.href="${bashPath}/person/main.do"
	  	      }else{
	  	    	 layer.msg(result.msg);
	  	      }   	
	  	   },
	  	  error:function(result){
	  		$("#submitBtn").bind("click",login);
	  	       layer.msg("内部错误");
	  	   }
	  }; 
	  $("#login_form").ajaxSubmit(appData);  
  }
  		
  


  function checkParams(){
     var loginName2 = $("#loginName").val();
     var password2 = $("#password").val();
     if(loginName2==null||loginName2==""||password2==null||password2==""){
    	 alert("请填完整数据");
    	 return false;
     }
      return true;
  }
  </script>
  
</body>
</html>