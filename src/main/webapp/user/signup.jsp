<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>Sign Up</title>
</head>
<body>
<!-- c:url implements url rewriting -->
<br>
<c:if test="${sessionScope.lastVisitUrl eq null }">
	<c:set var="lastVisitUrl" value="/my-blog/index.jsp" scope="session"></c:set>
</c:if>
<form action='<c:url value="/SignUp"/>' method="post" enctype="multipart/form-data" accept-charset="utf-8">
	username: <input type="text" name="username" required>
	<br>
	email: <input type="email" name="email" required>
	<br>
	password: <input type="text" name="password" required>
	<br>
	profile: <input type="file" name="profile">
	<br>
	city: <input type="text" name="city">
	<br>
	<input type="submit" value="sign up">
</form>
<%@ include file="/footer/footer.jsp" %>
</body>
</html>