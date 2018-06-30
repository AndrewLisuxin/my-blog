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
String createTime = ft.format(article.getCreateTime());
String lastUpdateTime = ft.format(article.getLastUpdateTime());
User me = (User)request.getSession().getAttribute("user");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>suxinli--<%=article.getTitle() %></title>
</head>
<body>
<a href="<%=response.encodeURL("index.jsp") %>">Home</a>
<article>
<h3><%=article.getTitle() %></h3>
<h5>
create time: <%=createTime %> , last update time: <%=lastUpdateTime %>
<br>
views(<%=article.getVisit() %>) likes(<%=article.getLike() %>) 
</h5>
<form action="<%=response.encodeRedirectURL("LikeArticleServlet") %>" method="post">
	<input type="submit" value="like"
	<%
		if(me == null) {
	%>
			disabled
	<%		
		}
	%>
	>
</form>
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
<!-- the jsp page is dispatchered from ViewArticleServlet, so the current dir is context root -->
<%
List<Comment> comments = (List<Comment>)request.getAttribute("comments");
if(comments != null) {
	for(Comment comment : comments) {
		User user = comment.getUser();
%>
		<B><%=user.getUsername() %></B> <%= ft.format(comment.getCreateTime()) %> from <%=user.getCity() %>
		<br>
		<img src="ProfileServlet?profile=<%=user.getImage()%>" width="50" height="50">
		<%=comment.getContent() %>
		<%
		if(me != null && user.getId() == me.getId()) {
		%>
		<form action="<%=response.encodeURL("DeleteCommentServlet?id=" + comment.getId())%>" method="post">
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
<form action="<%=response.encodeURL("CreateCommentServlet")%>" method="post" id="comment">
<input type="submit" value="submit"
<%
if(me == null) {
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