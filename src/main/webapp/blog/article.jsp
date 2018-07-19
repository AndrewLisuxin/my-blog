<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.suxinli.model.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*,java.util.Calendar,java.util.TimeZone"
%>
<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://suxinli.com/jsp/tlds/mytags" prefix="mytags" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>suxinli--${article.title }</title>
</head>
<body>
<%@ include file="/user/header.jsp" %>
<article>
<h3>${article.title }</h3>
<h5>
create time: <mytags:formatTime time="${article.createTime }"/> , last update time: <mytags:formatTime time="${article.lastUpdateTime }"/>
<br>
views(${article.visit }) likes(${article.like }) 
</h5>
<form action='<c:url value="/LikeArticle"></c:url>' method="post">
	<input type="submit" value="like"
	<c:if test="${not logged}">
		disabled
	</c:if>
	>
</form>
<hr>
<br>

<pre>${article.content }</pre>

</article>

<br>
<br>
<br>
<br>

<h4>Comments</h4>
<hr/>
<!-- the jsp page is dispatchered from ViewArticleServlet, so the current dir is context root -->
<c:if test="${requestScope.comments ne null }">
	<c:forEach items="${requestScope.comments }" var="comment">
		<b>${comment.user.username }</b> <mytags:formatTime time="${comment.createTime }"/> from ${comment.user.city }
		<br>
		<img src="Profile?profile=${comment.user.image }" width="50" height="50">
		<pre>${comment.content }</pre>
		<c:if test="${logged and sessionScope.user.id eq comment.user.id}">
			<form action="<c:url value='/DeleteComment?id=${comment.id }'/>" method="post">
				<input type="submit" value="delete">
			</form>
		</c:if>
		<hr>
	</c:forEach>
</c:if>


<br>
<br>
<br>
<br>


<c:choose>
	<c:when test="${logged}">
		<form action='<c:url value="/CreateComment"></c:url>' method="post" id="comment">
			<input type="submit" value="submit">
		</form>
		<textarea name="content" form="comment" required></textarea>
	</c:when>
	<c:otherwise>
		<font color="red">you must log in before write a comment!</font>
	</c:otherwise>
</c:choose>

<%@ include file="/footer/footer.jsp" %>
</body>
</html>