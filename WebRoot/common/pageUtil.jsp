<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div style="text-align:center;">
	    <c:if test="${totalPage>1}">
	                第${pageNo}页，共${totalPage}页
	        <a href="javascript:jumpPage(1)" style="text-decoration:none"><font color="blue">首页</font></a>
	        <c:if test="${pageNo > 1}">
	              <a href="javascript:jumpPage(${pageNo-1});" style="text-decoration:none"><font color="blue">上一页</font></a>       
	          </c:if>
	          <c:if test="${pageNo < totalPage}">
	              <a href="javascript:jumpPage(${pageNo+1});" style="text-decoration:none"><font color="blue">下一页</font></a>       
	          </c:if>
	        <a href="javascript:jumpPage('${totalPage}')" style="text-decoration:none"><font color="blue">未页</font></a>
	     </c:if>
	     <c:if test="${totalPage<=1}">
	                第1页, 共1页
	     </c:if>        
</div>