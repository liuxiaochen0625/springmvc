<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单编辑</title>
   <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
   <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
</head>
<body >
   <div region="center"  style="padding:5px;">
       <div class="bt chdt" style="border-top:1px solid #fb8410;">
          <form name="menuForm" method="post" id="menuForm">
             <input type="hidden" name="menuID" id="menuID1" value="${menuID}"/>
             <input type="hidden" name="menuLevel" id="menuLevel1" value="${menu.menuLevel}"/>
               <table class="info" cellspacing=1 cellpadding="0" width="80%">
                  <tr>
                     <th style="text-align:center"colspan="2">菜单权限编辑</th>
                  </tr>
                  <tr>
                     <td width="30%" style="text-align:center"> 父菜单</td>
                     <td>
                        <select name="parentMenuID" id="parentMenuID">
                           <option value="-1">--创建新的父菜单--</option>
                           <c:choose>
                              <c:when test="${not empty menu && menu.parentMenuID eq -1}">                                                                
                              </c:when>
                              <c:otherwise>
                                  <c:if test="${not empty menuList}">
                                     <c:forEach items="${menuList }" var="c">
                                        <c:if test="${menuID != c.menuID }">
                                           <option value="${c.menuID}">${c.menuName}</option>
                                        </c:if>                          
                                      </c:forEach>
                                  </c:if>
                              </c:otherwise>
                           </c:choose>
                        </select>
                        <script type="text/javascript">$("#parentMenuID").val('${menu.parentMenuID}');</script>
                     </td>
                  </tr>
                  <tr>
                     <td style="text-align:center">菜单名称</td>
                     <td>
                        <input type="text" maxlength="10"  id="menuName1" name="menuName" value="${menu.menuName }"/>
                     </td>            
                  </tr>
                  <tr>
                     <td style="text-align:center">菜单地址</td>
                     <td>
                        <input type="text" maxlength="200"  id="menuURL1" name="menuURL" value="${menu.menuURL}"/>
                     </td>
                  </tr>
                  <tr>
                     <td style="text-align:center">排序</td>
                     <td>
                         <input type="text" maxlength="5" id="menuOrder1" name="menuOrder" value="${menu.menuOrder}">
                         (说明：数值越大越靠前。)
                     </td>
                  </tr>
                  <tr>
                     <td colspan="2">
                         <center>
                            <input type="button" value="确认" onclick="addOpAuority();">
                            <input type="button" value="取消" onclick="closePrompt();"/>
                         </center>  
                     </td>
                  </tr>
               </table>
          </form>   
       </div>
   </div>
   <script type="text/javascript">
   function addOpAuority(){
       var menuName=$("#menuName1").val();     
       if(menuName==''){
           alert("菜单名称不能为空！");
           return false;
       }
       var parentMenuID=$("#parentMenuID").val();
       if(parentMenuID==-1){
           $("#menuLevel1").val(1);
           $("#menuURL1").val("");
       }else{
    	   var menuURL=$.trim($("#menuURL1").val());          
           if(menuURL==''){
               alert("创建子菜单时，菜单地址不能为空！");
               return false;
           }
    	   $("#menuLevel1").val(2);
       }
       var checkOrderNo=/^[0-9]*$/;
       var menuOrder=$.trim($("#menuOrder1").val());
       if(menuOrder==''){
           $("#menuOrder1").val("0");
       }else{
           if(!checkOrderNo.test(menuOrder)){
        	   alert("排序号只能是数字！");
               $("#menuOrder1").val("0");
               return false;
           }
       }
       $.ajax({
		   url: '${ctx}/user/menuModify.action',
		   type: 'POST',
		   cache:false,
		   data: $("#menuForm").serializeArray(),
		   success: function(data){
			   var flag = data.flag;
			   var msg = data.msg;
			   alert(msg);			   
			   if(flag){
				   window.location.reload(true);
			   }
		   }
	   });
   }
   </script>
</body>
</html>