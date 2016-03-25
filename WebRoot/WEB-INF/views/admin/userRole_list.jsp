<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户角色管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
</head>
<body >
    <div class="bt chdt" style="border-top:1px solid #fb8410;">
       <input type="hidden" id="userID" value="${userID }"/>
       <table cellspacing="5" cellpadding="5"class="bt" style="width:100%">
         <tr>
            <td>
               <input type="button" value="赋予角色" onclick="grantRole();"/>
            </td>
         </tr>
      </table> 
       <table cellspacing="0" cellpadding="1" style="width:100%;"class="newinfo">
          <tr class="meter">
             <td>选择</td>
             <td>角色ID</td>
             <td>角色编码</td>
             <td>角色名称</td>
          </tr>
          <c:if test="${not empty roleList}">
             <c:forEach items="${roleList }" var="c">
                <tr>
                   <td><input type="checkbox" value="${c.roleID }" <c:if test="${fn:contains(userRoles,c.roleCode)}">checked</c:if> /></td>
                   <td>${c.roleID }</td>
                   <td>${c.roleCode }</td>
                   <td>${c.roleName }</td>
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
       function grantRole(){
           var userID=$("#userID").val();
           var roleIDs="";
           $("input[type='checkbox']:checked").each(function(){
               roleIDs +=$(this).val()+",";
           });
           if(userID==''){
               alert("参数异常--用户ID为空！");
           }
           $.post("${ctx}/user/grantUserRole?ranNum="+Math.random,{userID:userID,roleIDs:roleIDs},
                   function(data){
      		        $("#myBody").toggleLoading({close:true});
                      var msg=data.msg;
                      if(msg=='ok'){
                          alert("授权成功！");                           
                      }else{
                          alert(msg);
                      }
                      window.location.reload(true); 
                   },"json"
            );
       }
    </script>
</body>
</html>