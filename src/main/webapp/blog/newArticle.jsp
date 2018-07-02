<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Article</title>
</head>
<body>
<a href="<%=response.encodeURL("../index.jsp") %>">Home</a>
<br>
<form action="<%=response.encodeURL("../CreateArticle")%>" method="post" id="article_form"> 
title
<br>
<input type="text" name="title" required>
<input type="submit" value="save">
</form>
<textarea rows="70" cols="600" name="content" form="article_form" required></textarea>
</body>
</html>