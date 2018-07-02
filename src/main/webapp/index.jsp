<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.suxinli.model.*" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="com.suxinli.config.*" %>
<%@ page import="java.util.concurrent.locks.Lock"%>
<%@ page import="java.util.concurrent.locks.ReentrantReadWriteLock" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>blog</title>
</head>
<body>
<!-- check if the user has logged in -->
<div align="right">
<% 
User user = (User)session.getAttribute("user"); 
boolean isAdmin = (user != null && user.getEmail().equals(Configuration.get("admin_email")));
if(user == null) {
%>
	<form action="Login" method="post">
	email: <input type="email" name="email" required>
	password: <input type="password" name="password" required>
	<input type="submit" value="log in">
	</form> 
	<a href="user/signup.jsp">sign up</a>
<%
}
else {
%>
	<img src="Profile?profile=<%=user.getImage()%>" width="50" height="50">
	<a href="<%=response.encodeURL("user/user.jsp") %>"><%=user.getUsername() %></a>

	<form action="<%=response.encodeURL("Logout")%>" method="post">
	<input type="submit" value="logout">
	</form>
<% 
	
}
%>
</div>
<hr>

<h3>Articles</h3>
<%
if(isAdmin) {
%>
	<a href="<%=response.encodeURL("blog/newArticle.jsp")%>">write a new article</a>
<% 
}
%>
<ul>
<%	

	Lock r = Article.getReadLock();
	r.lock();
	for(Pair<Integer, String> articleItem : Article.getArticleList()) {
		int id = articleItem.getKey();
		%>
		<li>
			<a href="<%=response.encodeURL("ViewArticle?id="+id) %>"><%=articleItem.getValue() %></a>
			<%
			if(isAdmin) {
			%>
				<form action="<%=response.encodeURL("DeleteArticle?id="+id) %>" method="post"><input type="submit" value="delete"></form>
				<a href="<%=response.encodeURL("UpdateArticle?id="+id) %>"><button>edit</button></a>
			<% 
			}
			%>
		</li>
		<%
	}
	r.unlock();
%>
</ul>
</body>
</html>