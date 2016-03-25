<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色菜单权限管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
    <link rel="stylesheet" id="easyuicssId" type="text/css" href="${ctx}/widgets/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/jquery-easyui/themes/icon.css"> 
    <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no" id="myBody">
    <div region="center"  style="padding:5px;">    
    <div class="bt chdt" style="border-top:1px solid #fb8410;">
       <input type="hidden" id="roleID" value="${roleID }"/>
       <table cellspacing="5" cellpadding="5"class="bt" style="width:100%">
         <tr>
            <td>
               <input type="button" value="授予菜单权限" onclick="grantOP();"/>
            </td>
         </tr>
       </table>
       <table cellspacing="0" cellpadding="1" style="width:100%;"class="newinfo">
          <c:if test="${not empty menuList}">
             <c:forEach items="${menuList }" var="c">
                 <tr class="meter">
                    <td colspan="2">${c.menuName}</td>
                 </tr>
                 <c:if test="${not empty c.childMenuList}">
                     <c:forEach items="${c.childMenuList }" var="m">
                        <tr>
                          <td>
                            <input type="checkbox" id="menu_${m.menuID }" value="${m.menuID }"/>&nbsp;&nbsp;&nbsp;${m.menuName}
                          </td>
                          <td>
                            <a href="javascript:void(0);" onclick="roleMenuOpMng('${m.menuID}');bgChange(this);"><font color="blue">按钮权限</font></a>
                          </td>
                        </tr>
                     </c:forEach>
                 </c:if>
             </c:forEach>
          </c:if>
          <c:if test="${empty menuList}">
             <tr>
                 <td colspan="2">暂无菜单权限</td>
             </tr>
          </c:if>
          <c:if test="${not empty roleMenuList}">
             <c:forEach items="${roleMenuList }" var="c">
                 <script type="text/javascript">
                    var antiElectionMenuID='${c.menuID}';
                    $("#menu_"+antiElectionMenuID).attr("checked","checked");
                 </script>
             </c:forEach>
          </c:if>
       </table>      
    </div>
    <script type="text/javascript">
        function bgChange(obj){           
 	       $(obj).closest("table").find("tr").each(function(){
     	      $(this).removeAttr("bgColor");
     	   });
 	       $(obj).closest("tr").attr("bgColor","#B6DC73"); 
        }
        function roleMenuOpMng(menuID){
            var roleID=$("#roleID").val();
            if(roleID=='' || menuID==''){
                alert("参数异常---角色或菜单ID 为空！");
                return false;
            }
            var src="${ctx}/user/roleMenuOpMng.action?roleID="+roleID+"&menuID="+menuID;
            $("#menuOpDiv").html("");
            $("#menuOpDiv").html('<iframe scrolling="auto"  frameborder="0"  src='+src+' style="width:100%;height:100%;"></iframe>');
        }
        function grantOP(){
            var roleID=$("#roleID").val();
            if(roleID==''){
                alert("参数异常--角色ＩＤ为空！");
                return false;
            }
            var menuIDs="";
            $("input[type='checkbox']:checked").each(function(){
                menuIDs +=$(this).val()+",";
            });
            $("#myBody").toggleLoading({
      			msg : '数据处理中,请耐心等候...',
      		    show:true
      	   });
            $.post("${ctx}/user/grantRoleMenuAuthority?ranNum="+Math.random,{roleID:roleID,menuIDs:menuIDs},
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
    </div>
    <div region="east"  id="menuOpDiv" iconCls="icon-reload" split="true" title="菜单按钮权限" style="width:350px;">
   </div>  
</body>
</html>