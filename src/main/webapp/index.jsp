<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.suxinli.model.*" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="com.suxinli.config.*" %>
<%@ page import="java.util.concurrent.locks.*"%>
<%@ page import="java.util.concurrent.locks.ReentrantReadWriteLock.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>blog</title>
</head>
<body>
<c:set var="lastVisitUrl" value="/my-blog/index.jsp" scope="session"></c:set>
<!-- check if the user has logged in -->
<%@ include file="/WEB-INF/header/header.jsp" %>

<h3>Articles</h3>

<c:if test="${isAdmin }">
	<a href="<c:url value='/CreateArticle'/>">write a new article</a>
</c:if>

<ul>

${applicationScope.articleReadLock.lock() }
<%--
((ReadLock)application.getAttribute("articleReadLock")).lock();
--%>

	<c:forEach items="${applicationScope.articleList }" var="articleItem">
		<li>
			<a href='<c:url value="ViewArticle?id=${articleItem.key }"/>'>${articleItem.value }</a>
			<c:if test="${isAdmin }">
				<form action='<c:url value="DeleteArticle?id=${articleItem.key }"/>' method="post"><input type="submit" value="delete"></form>
				<a href='<c:url value="UpdateArticle?id=${articleItem.key }"/>'><button>edit</button></a>
			</c:if>
		</li>
	</c:forEach>
${applicationScope.articleReadLock.unlock() }	
	
<%--
((ReadLock)application.getAttribute("articleReadLock")).unlock();
--%>
</ul>
</body>
</html>