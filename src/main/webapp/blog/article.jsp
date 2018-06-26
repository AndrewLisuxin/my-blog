<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.suxinli.model.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<%
Article article = (Article)request.getAttribute("article"); 
SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String time = ft.format(article.getCreateTime());
User me = (User)request.getSession().getAttribute("user");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>suxinli--<%=article.getTitle() %></title>
</head>
<body>
<a href="index.jsp">Home</a>
<article>
<h3><%=article.getTitle() %></h3>
<h5>
publish time: <%=time %></h5>
<hr>
<br>
<p>
<%=article.getContent() %>
</p>
</article>

<br>
<br>
<br>
<br>

<h4>Comments</h4>
<hr/>
<%
List<Comment> comments = (List<Comment>)request.getAttribute("comments");
if(comments != null) {
	for(Comment comment : comments) {
		User user = comment.getUser();
%>
		<B><%=user.getUsername() %></B> <%= ft.format(comment.getCreateTime()) %> 
		<br>
		<img src="ProfileServlet?profile=<%=user.getImage()%>" width="50" height="50">
		<%=comment.getContent() %>
		<%
		if(me != null && user.getId() == me.getId()) {
		%>
		<form action=<%="DeleteCommentServlet?id=" + comment.getId()%> method="post">
			<input type="submit" value="delete">
		</form>
		<%
		}
		%>
		<hr/>
		
<% 
	}
}
%>

<br>
<br>
<br>
<br>
<form action="CreateCommentServlet" method="post" id="comment" accept-charset="UTF-8">
<input type="submit" value="submit"
<%
if(request.getSession().getAttribute("user") == null) {
%>
	disabled> <font color="red">you must log in before write a comment!</font>
<%
}
else {
%>
	>
<%
}
%>

</form>
<textarea name="content" form="comment" required></textarea>
</body>
</html>