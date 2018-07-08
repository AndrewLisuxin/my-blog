<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>Error</title>
</head>
<body>
<h1><%=response.getStatus() %></h1>

<div>
<%
Throwable e = exception;
while(e != null) {
%>
	<h2><%=e.getMessage() %><h2>
	
<% 
	e = e.getCause();
}
%>
</div>

<a href='<c:url value="/index.jsp"/>'>Home</a>
</body>
</html>