<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
	import="com.suxinli.model.*"    
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Article</title>
</head>
<%
Article article = (Article)request.getAttribute("article");
%>
<body>
<a href="<%=response.encodeURL("/my-blog/index.jsp") %>">Home</a>
<br>
<form action="<%=response.encodeURL("/my-blog/UpdateArticle")%>" method="post" id="article">
title: <input type="text" name="title" value="<%=article.getTitle() %>"required>
<input type="submit" value="update">
</form>
<textarea rows="30" cols="300" name="content" form="article" required><%=article.getContent()%></textarea>
</body>
</html>