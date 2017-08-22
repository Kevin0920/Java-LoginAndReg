<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Event Edit</title>
</head>
<body>
	<h1>Got Party</h1>
	<hr>
	<h3>Edit Event</h3>
	<hr>
	<form:form method="POST" action="/events/edit/${event.id}" modelAttribute="event">
			<form:label path="name">Name
		    <form:errors path="name"/>
		    <form:input path="name" value="${event.name}"/></form:label>
			<br><br>
	   		<form:label path="date">Date:</form:label>
	        <form:input path="date" type="date" value="${event.date }"/>
	   		<br><br>
        		<form:label path="location">Location:</form:label>
			   <form:select  path="location">
			     <form:option value="state"> --SELECT--</form:option>
			     <form:options items="${states}"></form:options>
			</form:select>
			<br><br>
	   <input type="submit" value="Edit Event"/>
	</form:form>

	

</body>
</html>