<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理系统</title>
<link href="/css/default.css" rel="stylesheet" type="text/css" />
</head>
<body class="greenbg">
<div class="loginbox">
    <div class="loginbody">
    <form id="myForm" method="post" action="${ctx }/j_spring_security_check">
       <p>
	     <label>账     号：</label>
	     <input type="text" id="j_username" name="j_username" >
	   </p>
	   <p>
	      <label>密　码：</label>
	      <input maxlength="12" type="password" id="j_password" name="j_password" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false">
	   </p>
	   <p>
	      <input class="loginbtn" type="button" value="" onclick="submitForm();"/>
	      <input class="cancelbtn" type="button" value="" onclick="cleardata();"/>
	   </p>
	   <p>
	   </p>
	   <p>
	     <s:fielderror fieldName="errMessage" cssStyle="color: red; line-height: 40px;" theme=""><font color="red" id="errMng">${msg }</font></s:fielderror>
	   </p>
    </form>
   </div> 
    <script type="text/javascript">
    function submitForm(){
 	   var j_username=$.trim($("#j_username").val());
 	   var j_password=$.trim($("#j_password").val());
 	   if(j_username==''){
 		   alert("请输入你的账号！");
 		   return false;
 	   }
 	   if(j_password==''){
 		   alert("请输入你的密码！");
 		   return false;
 	   }
 	   $("#showMsg").html("");
 	   $("#myForm").submit();
    }
    function cleardata(){
 	   $("#j_username").val("");
 	   $("#j_password").val("");
 	   $("#showMsg").html("");
    }
    </script>
</div>        
</body>
</html>
