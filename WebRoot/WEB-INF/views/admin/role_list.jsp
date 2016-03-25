<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
    <link rel="stylesheet" id="easyuicssId" type="text/css" href="${ctx}/widgets/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/jquery-easyui/themes/icon.css"> 
    <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
    <div region="center"  style="padding:5px;">
    <div class="bt chdt" style="border-top:1px solid #fb8410;">
       <table cellspacing="0" cellpadding="1" style="width:100%;"class="newinfo">
          <tr class="meter">
             <td>角色ID</td>
             <td>角色编码</td>
             <td>角色名称</td>
             <td>菜单权限管理</td>
          </tr>
          <c:if test="${not empty roleList}">
             <c:forEach items="${roleList }" var="c">
                <tr>
                   <td>${c.roleID }</td>
                   <td>${c.roleCode }</td>
                   <td>${c.roleName }</td>
                   <td><input type="button" value="角色权限管理" onclick="roleMenuMng('${c.roleID}');bgChange(this);"/></td>
                </tr>
             </c:forEach>
          </c:if>
          <c:if test="${empty roleList}">
             <tr>
                <td colspan="4">暂无角色</td>
             </tr>
          </c:if>
       </table>      
    </div>
    <script type="text/javascript">
       //变色
       function bgChange(obj){           
    	   $(obj).closest("table").find("tr").each(function(){
        	   $(this).removeAttr("bgColor");
        	});
    	   $(obj).closest("tr").attr("bgColor","#B6DC73"); 
       }
       //角色对应的菜单权限管理
       function roleMenuMng(roleID){
           var src="${ctx}/user/roleMenuMng.action?roleID="+roleID;
           $("#roleMenuDiv").html("");
           $("#roleMenuDiv").html('<iframe scrolling="auto"  frameborder="0"  src='+src+' style="width:100%;height:100%;"></iframe>');
       }
    </script>
    </div>
    <div region="east"  id="roleMenuDiv" iconCls="icon-reload" split="true" title="角色权限管理" style="width:700px;">
   </div>  
</body>
</html>