<c:set var="logged" value="${sessionScope.user ne null }"></c:set>
<c:set var="isAdmin" value="${logged and sessionScope.user.email eq applicationScope.adminEmail }"></c:set>
<div align="right">


<c:choose>
	<c:when test="${not logged }">
		<form action="Login" method="post">
		email: <input type="email" name="email" required>
		password: <input type="password" name="password" required>
		<input type="submit" value="log in">
		</form> 
		<a href="<c:url value="/SignUp"/>">sign up</a>
	</c:when>
	<c:otherwise>
	<%-- session = "false", then cannot use the session implicit object --%>
		
		<img src="/my-blog/Profile?profile=${sessionScope.user.image }" width="50" height="50">
		<a href="<c:url value='/UpdateUser'/>">${sessionScope.user.username }</a>

		<form action='<c:url value="/Logout"></c:url>' method="post">
		<input type="submit" value="logout">
		</form>
		
	</c:otherwise>
</c:choose>
</div>
<hr>