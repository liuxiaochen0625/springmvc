<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
    <link rel="stylesheet" id="easyuicssId" type="text/css" href="${ctx}/widgets/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/jquery-easyui/themes/icon.css"> 
    <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css"> 
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
   <div region="center"  style="padding:5px;">
   <div class="bt chdt" style="border-top:1px solid #fb8410;">
   <form id="myForm" name="myForm" action="${ctx}/user/userMng.action" method="post">
      <input type="hidden" name="pageNo" id="pageNo" value="${pageNo }"/>
      <input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
      <table cellspacing="5" cellpadding="5"class="bt" style="width:100%">
         <tr>
            <td>
                                         用户名:<input type="text" name="userName" value="${userName }"/>
               <input type="button" value="查询" onclick="jumpPage(1);"/>
            </td>
         </tr>
      </table>      
      <table cellspacing="0" cellpadding="1" style="width:100%;"class="newinfo">
         <tr class="meter">
            <td>登录名</td>
            <td>真实姓名</td>
            <td>电话</td>
            <td>操作人</td>
            <td>状态</td>
            <td>角色</td>
         </tr>
         <c:if test="${not empty userList}">           
            <c:forEach items="${userList }" var="c">
                <tr>
                   <td>${c.userName }</td>
                   <td>${c.realName }</td>
                   <td>${c.phone}</td>
                   <td>${c.operator }</td>
                   <td>
                      <c:choose>
                         <c:when test="${c.flag eq 1}">有效</c:when>
                         <c:otherwise>无效</c:otherwise>
                      </c:choose>
                   </td>
                   <td>
                      <input type="button" value="角色编辑" onclick="userRoleMng('${c.userID}');bgChange(this);"/>
                   </td>
                </tr>  
            </c:forEach>
         </c:if>
         <c:if test="${empty  userList}">
            <tr><td colspan="6">查无数据</td></tr>
         </c:if>
      </table>
   </form>
   <!-- 跳转 -->
   <c:if test="${not empty userList}">
       <jsp:include page="/common/pageUtil.jsp"/>
   </c:if>
   </div>
   <script type="text/javascript">
       function jumpPage(pageNo){
           $("#pageNo").val(pageNo);
           $("#myForm").submit();
       }
     //变色
       function bgChange(obj){           
    	   $(obj).closest("table").find("tr").each(function(){
        	   $(this).removeAttr("bgColor");
        	});
    	   $(obj).closest("tr").attr("bgColor","#B6DC73"); 
       }
       function userRoleMng(userID){
           var src="${ctx}/user/userRoleMng.action?userID="+userID;
    	   $("#userRoleDiv").html("");
           $("#userRoleDiv").html('<iframe scrolling="auto"  frameborder="0"  src='+src+' style="width:100%;height:100%;"></iframe>');
       }
   </script>
   </div>
   <div region="east"  id="userRoleDiv" iconCls="icon-reload" split="true" title="用户角色管理" style="width:450px;">
   </div>   
</body>
</html>