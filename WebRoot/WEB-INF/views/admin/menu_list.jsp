<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单权限管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/dialog.css">
    <script type="text/javascript" src="${ctx}/js/dialog.js"></script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no" id="myBody">
    <div region="center"  style="padding:5px;">    
    <div class="bt chdt" style="border-top:1px solid #fb8410;">  
        <table cellspacing="5" cellpadding="5"class="bt" style="width:100%">
            <tr>
               <td>
                                              菜单名称：<input type="text" name="menuName" id="menuName" value="${menuName}"/>
                                              菜单地址：<input type="text" name="menuURL" id="menuURL" value="${menuURL}"/>
                 <input type="button" value="查询" />
                 <input type="button" value="新增" onclick="toAdd();"/>
               </td>
            </tr>
          </table>
          <script type="text/javascript">
            //新增菜单
            function toAdd(){
            	dialog("菜单权限添加","url:get?/user/toMenuModify.action","590px","302px","from","190");
            } 
            //修改菜单
            function toUpdate(menuID){
            	dialog("菜单权限修改","url:get?/user/toMenuModify.action?menuID="+menuID,"590px","302px","from","190");
            }
          </script>                  
          <c:if test="${not empty menuList}">
             <table cellspacing="0" cellpadding="1" style="width:100%;"class="newinfo">
                 <tr class="meter">
                    <td>菜单ID</td>
                    <td>菜单名称</td>
                    <td>菜单地址</td>
                    <td>操作</td>
                 </tr>
                 <c:forEach items="${menuList }" var="c">
                     <tr class="menu_title" bgColor="E8D098" >
                        <td colspan="4" style="text-align:center;"><span>${c.menuName}</span>
                           <a href="javascript:void(0);" onclick="toUpdate('${c.menuID}');bgChange(this);"><font color="blue">修改</font></a>
                        </td>
                     </tr>
                     <c:if test="${not empty c.childMenuList}">
                        <c:forEach items="${c.childMenuList }" var="m">
                            <tr>
                               <td>${m.menuID }</td>
                               <td>${m.menuName}</td>
                               <td>${m.menuURL }</td>
                               <td>
                                    <a href="javascript:void(0);" onclick="toUpdate('${m.menuID}');bgChange(this);"><font color="blue">修改</font></a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="roleMenuOpMng('${m.menuID}');bgChange(this);"><font color="blue">按钮权限设置</font></a> 
                               </td>
                            </tr>
                        </c:forEach>
                     </c:if>
                 </c:forEach>
             </table>
          </c:if>
          <c:if test="${empty menuList}">
            <table cellspacing="0" cellpadding="1" style="width:100%;"class="newinfo">
               <tr>
                 <td colspan="2">暂无菜单权限</td>
               </tr>
             </table>
          </c:if>   
    </div>
    <script type="text/javascript">
        function bgChange(obj){           
           $("tr").each(function(){
       	      $(this).removeAttr("bgColor");
       	   });
   	       $(obj).closest("tr").attr("bgColor","#B6DC73"); 
   	       $(".menu_title").attr("bgColor","#E8D098");
        }
        function roleMenuOpMng(menuID){
            var src="${ctx}/user/menuOpMng.action?menuID="+menuID;
            $("#menuOpDiv").html("");
            $("#menuOpDiv").html('<iframe scrolling="auto"  frameborder="0"  src='+src+' style="width:100%;height:100%;"></iframe>');
        }
        function closePrompt(){
       	     $("#floatBoxBg").remove();
      	     $("#floatBox").remove();
      	     dialogFirst=true;	
       }
    </script>
    </div>
    <div region="east"  id="menuOpDiv" iconCls="icon-reload" split="true" title="菜单按钮权限" style="width:500px;">
   </div>  
</body>
</html>