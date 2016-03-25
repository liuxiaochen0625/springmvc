<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试</title>
   <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
   <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
   <script type="text/javascript" src="${ctx}/js/FilterXSS.js"></script>
</head>
<body>
   <div class="bt chdt" style="border-top:1px solid #fb8410;">
      <table class="info" cellspacing=1 cellpadding="0" width="80%">
         <tr>
            <td>
              Xss:<input type="text" id="xss" style="width:500px;" value=""&gt;&lt;svg onload=alert(9)&gt;"/>
              <input type="button" onclick="scriptXSS();" value="FileterXSS"/>
            </td>
         </tr>
      </table>
   </div>       
   <script type="text/javascript">
      function  scriptXSS(){
         var xss = $("#xss").val();
         alert(xss);
         xss= filterXSS (xss, '');
         $("#xss").val(xss);
         xss = $("#xss").val();
         alert(xss);
      }
   
   </script>
</body>
</html>