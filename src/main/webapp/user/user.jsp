<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.suxinli.model.*" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>User</title>
</head>
<body>

<hr/>

<jsp:useBean id="user" class="com.suxinli.model.User" scope="session"/>
	email: <jsp:getProperty name="user" property="email"/>
<br>
<form action='<c:url value="/UpdateUser"/>' method="post" enctype="multipart/form-data">
	username: <input type="text" name="username" value='<jsp:getProperty name="user" property="username"/>' required>
	<br>
	password: <input type="text" name="password" value='<jsp:getProperty name="user" property="password"/>' required>
	<br>
	profile: <img src='/my-blog/Profile?profile=<jsp:getProperty name="user" property="image"/>' width="100" height="100">
	<input type="file" name="profile">
	<br>
	city: <input type="text" name="city" value='<jsp:getProperty name="user" property="city"/>'>
	<br>
	<input type="submit" value="update">
</form>

<%@ include file="/footer/footer.jsp" %>

</body>
</html>