<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
   <link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
   <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
</head>
<body id="myBody">
<div region="center"  style="padding:5px;">
    <div class="bt chdt" style="border-top:1px solid #fb8410;">
       <form name="myForm" id="myForm" method="post" enctype="multipart/form-data">
          <table class="info" cellspacing=1 cellpadding="0" width="80%">
             <tr>
                <th style="text-align:center" colspan="2"> 文件上传</th>
             </tr>
             <tr>
                <td>请选择上传的文件</td>
                <td>
                   <input type="file" id="file" size="40"  name="file"/>
                   <input type="button" value="上传" onclick="upload();"/>
                </td>
             </tr>
          </table>
       </form>
       <table class="info" cellspacing=1 cellpadding="0" width="80%">
          <tr>
             <th style="text-align:center" colspan="3"> 已经上传的文件</th>
          </tr> 
          <c:choose>
             <c:when test="${not empty fileList}">
                 <c:forEach items="${fileList }" var="c" varStatus="varStatus">
                    <c:if test="${varStatus.count eq 1 }">
                       <tr>
                    </c:if>                  
                       <td>
                           <img src="/temp/${c}" width="350" height="175"/><br/>
                           <a href="${ctx }/hadoop/downFile.action?fileName=${c}" ><font color="blue">下载</font></a>
                           &nbsp;&nbsp;
                           <a href="javascript:void(0);" onclick="delFile('${c}');"><font color="blue">删除</font></a>
                       </td>
                    <c:if test="${varStatus.count ne 1 && varStatus.count%3 eq 0}">
                       </tr>
                       <tr>
                    </c:if>   
                    <c:if test="${varStatus.end}">
                       </tr>
                    </c:if>
                 </c:forEach>
             </c:when>
             <c:otherwise>
                 <tr >
                   <td style="text-align:center" colspan="2">暂无上传的文件</td>
                 </tr>
             </c:otherwise>
          </c:choose>
       </table>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        //默认文件加载的方法
        $("#myForm").form({
            url:"${ctx}/hadoop/upload.action",
            onSubmit:function(){           	
            	$("#myForm").toggleLoading({
		              msg : '文件上传中,请耐心等候...',
		              show:true
	            });
            },
            success:function(data){
                $("#myForm").toggleLoading({close:true});
                var oTxt = jQuery.parseJSON(data);
			    var msg = oTxt.msg;	
			    var flag = oTxt.flag;
			    alert(msg);
			    if(flag){
				    window.location.reload(true);
				}
            }
        });
    });
    function upload(){
       var file = $("#file").val();
       if(file==null || file==''){
           alert("请选择上传的文件");
           return false;
       }
       $("#myForm").submit();
    }
    function delFile(fileName){
        if(fileName==null){
            return false;
        }
    	$("#myBody").toggleLoading({
  			msg : '数据处理中,请耐心等候...',
  		    show:true
  	    });
        $.post("${ctx}/hadoop/delFileByPath.action?ranNum="+Math.random,{fileName:fileName},
                function(data){
   		        $("#myBody").toggleLoading({close:true});
                   var msg=data.msg;
                   var flag= data.flag;
                   alert(msg);
                   if(flag){
                	   window.location.reload(true); 
                   }                
                },"json"
         );
    }

</script>
</body>
</html>