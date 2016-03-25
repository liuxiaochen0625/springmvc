<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单按钮权限设置</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
</head>
<body id="myBody" class="easyui-layout" style="overflow-y: hidden"  scroll="no" id="myBody">
    <div region="center"  style="padding:5px;"> 
    <div class="bt chdt" style="border-top:1px solid #fb8410;">
       <input type="hidden" id="roleID" value="${roleID }"/>
       <input type="hidden" id="menuID" value="${menuID }"/>
       <table cellspacing="5" cellpadding="5"class="bt" style="width:100%">
         <tr>
            <td>
               <input type="button" value="新增" />
            </td>
         </tr>
      </table> 
       <table cellspacing="0" cellpadding="1" style="width:100%;"class="newinfo">
          <tr class="meter">
             <td>按钮ID</td>
             <td>按钮编码</td>
             <td>按钮名称</td>
             <td>地址</td>
             <td>设置</td>
          </tr>
          <c:if test="${not empty opList}">
             <c:forEach items="${opList }" var="c">
                <tr>
                   <td>${c.operatorID }</td>
                   <td>${c.operatorCode }</td>
                   <td>${c.operatorName }</td>
                   <td>${c.operatorURL }</td>
                   <td>删除,编辑</td>
                </tr>
             </c:forEach>
          </c:if>
          <c:if test="${empty opList}">
             <tr>
                <td colspan="5">该菜单权限上还未有受限的按钮权限</td>
             </tr>
          </c:if>         
       </table>  
       <c:if test="${not empty roleOpList }">
          <c:forEach items="${roleOpList }" var="c">
             <script type="text/javascript">
                 var checkRoleOpID='${c.operatorID}';
                 $("#op_"+checkRoleOpID).attr("checked","checked");
             </script>
          </c:forEach>
       </c:if>    
    </div>
    </div>
    <script type="text/javascript">
      
    </script>
</body>
</html>