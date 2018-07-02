<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.suxinli.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User</title>
</head>
<body>
<a href="<%=response.encodeURL("/my-blog/index.jsp") %>">Home</a>
<hr/>
<%
User user = (User)session.getAttribute("user");
%>
email: <%=user.getEmail() %>
<br>
<form action="<%=response.encodeURL("../UpdateUser") %>" method="post" enctype="multipart/form-data" accept-charset="utf-8">
	username: <input type="text" name="username" value="<%=user.getUsername()%>" required>
	<br>
	password: <input type="text" name="password" value="<%=user.getPassword()%>" required>
	<br>
	profile: <img src="/my-blog/Profile?profile=<%=user.getImage()%>" width="100" height="100">
	<input type="file" name="profile">
	<br>
	city: <input type="text" name="city" value="<%=user.getCity()%>">
	<br>
	<input type="submit" value="update">
</form>
</body>
</html>