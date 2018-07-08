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
<!-- check if the user has logged in -->
<div align="right">

<c:set var="logged" value="${sessionScope.user ne null }" scope="request"></c:set>
<c:set var="isAdmin" value="${requestScope.logged and sessionScope.user.email eq applicationScope.adminEmail }" scope="session"></c:set>
<c:choose>
	<c:when test="${not requestScope.logged }">
		<form action="Login" method="post">
		email: <input type="email" name="email" required>
		password: <input type="password" name="password" required>
		<input type="submit" value="log in">
		</form> 
		<a href="user/signup.jsp">sign up</a>
	</c:when>
	<c:otherwise>
		<jsp:useBean id="user" type="com.suxinli.model.User" scope="session"/>
		<img src="Profile?profile=<jsp:getProperty name='user' property='image'/>" width="50" height="50">
		<a href="<c:url value='/user/user.jsp'/>"><jsp:getProperty name='user' property='username'/></a>

		<form action='<c:url value="/Logout"></c:url>' method="post">
		<input type="submit" value="logout">
		</form>
		
	</c:otherwise>
</c:choose>
</div>
<hr>

<h3>Articles</h3>

<c:if test="${sessionScope.isAdmin }">
	<a href="<c:url value='/blog/newArticle.jsp'/>">write a new article</a>
</c:if>

<ul>


<%	
((ReadLock)application.getAttribute("articleReadLock")).lock();
%>

	<c:forEach items="${applicationScope.articleList }" var="articleItem">
		<li>
			<a href='<c:url value="ViewArticle?id=${articleItem.key }"/>'>${articleItem.value }</a>
			<c:if test="${sessionScope.isAdmin }">
				<form action='<c:url value="DeleteArticle?id=${articleItem.key }"/>' method="post"><input type="submit" value="delete"></form>
				<a href='<c:url value="UpdateArticle?id=${articleItem.key }"/>'><button>edit</button></a>
			</c:if>
		</li>
	</c:forEach>
		
<%
((ReadLock)application.getAttribute("articleReadLock")).unlock();
%>
</ul>
</body>
</html>