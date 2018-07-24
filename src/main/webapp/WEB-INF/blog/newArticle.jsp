<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Article</title>
</head>
<body>
<br>
<form action='<c:url value="/CreateArticle"></c:url>' method="post" id="article_form"> 
title
<br>
<input type="text" name="title" required>
<input type="submit" value="save">
</form>
<textarea rows="70" cols="600" name="content" form="article_form" required></textarea>

<%@ include file="/WEB-INF/footer/footer.jsp" %>

</body>
</html>