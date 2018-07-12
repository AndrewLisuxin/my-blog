<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
	import="com.suxinli.model.*"    
%>
<!DOCTYPE>
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Article</title>
</head>

<body>
<a href='<c:url value="/index.jsp"></c:url>'>Home</a>
<br>
<form action='<c:url value="/UpdateArticle"></c:url>' method="post" id="article">
title: <input type="text" name="title" value="${article.title }"required>
<input type="submit" value="update">
</form>
<textarea rows="30" cols="300" name="content" form="article" required>
	${article.content }
</textarea>
</body>
</html>