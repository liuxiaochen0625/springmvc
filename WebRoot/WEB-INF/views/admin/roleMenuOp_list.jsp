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
</head>
<body id="myBody">
    <div class="bt chdt" style="border-top:1px solid #fb8410;">
       <input type="hidden" id="roleID" value="${roleID }"/>
       <input type="hidden" id="menuID" value="${menuID }"/>
       <table cellspacing="5" cellpadding="5"class="bt" style="width:100%">
         <tr>
            <td>
               <input type="button" value="授予按钮权限" onclick="grantRoleMenuOp();"/>
            </td>
         </tr>
      </table> 
       <table cellspacing="0" cellpadding="1" style="width:100%;"class="newinfo">
          <tr class="meter">
             <td>选择</td>
             <td>按钮ID</td>
             <td>按钮编码</td>
             <td>按钮名称</td>
          </tr>
          <c:if test="${not empty opList}">
             <c:forEach items="${opList }" var="c">
                <tr>
                   <td><input type="checkbox" value="${c.operatorID }" id="op_${c.operatorID }"/></td>
                   <td>${c.operatorID }</td>
                   <td>${c.operatorCode }</td>
                   <td>${c.operatorName }</td>
                </tr>
             </c:forEach>
          </c:if>
          <c:if test="${empty opList}">
             <tr>
                <td colspan="4">该菜单权限上还未有受限的按钮权限</td>
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
    <script type="text/javascript">
       function grantRoleMenuOp(){
           var roleID=$("#roleID").val();
           var menuID=$("#menuID").val();
           if(roleID=='' || menuID==''){
               alert("参数异常--角色ＩＤ或菜单ID为空！");
               return false;
           }
           var operatorIDs="";
           $("input[type='checkbox']:checked").each(function(){
               operatorIDs +=$(this).val()+",";
           });
           $("#myBody").toggleLoading({
     			msg : '数据处理中,请耐心等候...',
     		    show:true
     	   });          
           $.post("${ctx}/user/grantRoleMenuOpAuthority?ranNum="+Math.random,{roleID:roleID,menuID:menuID,operatorIDs:operatorIDs},
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