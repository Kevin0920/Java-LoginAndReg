<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Event Detail Page</title>
<style type="text/css">
.info{
  	padding: 20px;
    background-color: #E6E6FA;
    color: black;
	width:200px;
	font-weight: bold;
	font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 35%;
	display: inline-block;
}
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 35%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}

.message{
	margin-left: 700px;
}
</style>
</head>
<body>

	<h1>${messages.message }</h1>
	<hr>
	<form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
	<div class="info">
		<p>${event.host.firstName }</p>
		<p><fmt:formatDate value="${event.date}" type="date" pattern="yyyy-MMM-dd"/></p>
		<p>Location: <c:out value="${event.location}"/></p>
		<p>People who are attending this event: <c:out value="${event.users.size()}"/></p>

	</div>
		<table>
			<tr>
				<th>Name</th>
		    		<th>Location</th>
			</tr>
			<c:forEach var="user" items="${event.users}">
			<tr>
				<td><c:out value="${user.firstName}"/></td>
				<td><c:out value="${user.location}"/></td>
			</tr>
			</c:forEach>
		</table>

	<div class="message" >
		<h3>Message Wall</h3>
		<div>
	
			<c:forEach var="msg" items="${event.messages}">
				<p>${msg.name } say: ${msg.msg}</p>
			</c:forEach>
		</div>
		<form:form method="POST" action="/message" modelAttribute="message">
		<p>Add Comment:</p>
			<form:textarea path="msg" rows="2" cols="20"/>
			
			<form:hidden path="name" value="${event.host.firstName } ${event.host.lastName }"/>
			<form:hidden path="event" value="${event.id }"/>
			<input type="submit" value="Add a Comment">
		</form:form>

	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>