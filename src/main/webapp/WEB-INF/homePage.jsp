<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}
.creatTable {
	border-collapse: collapse;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
	<h4>${currentUser.lastName} ${currenUser.state}</h4>

    <h1>Welcome Page <c:out value="${currentUser.firstName}"></c:out></h1>
    <hr>
    <h3>Here are some of the events in your state:</h3>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <hr>
        <table>
	    		<tr>
	    			<th>Name</th>
	    			<th>Date</th>
	    			<th>Location</th>
	    			<th>Host</th>
	    			<th>Action/Status</th>
	    		</tr>
    		<c:forEach var="event" items="${events}">
		<c:if test="${currentUser.state == event.state }">
    		<tr>
    			<td><a href="/events/${event.id }">${event.name}</a></td>
    			<td><fmt:formatDate value="${event.date}" type="date" pattern="MMM-dd-yyyy"/></td>
    			<td><c:out value="${event.location}"/></td>
    			<td><c:out value="${event.host.firstName}"/></td>
    			
    			<c:if test="${event.host.id == currentUser.id }">
	    			<td><a href="/events/edit/${event.id}">Edit</a>
	    			<td><a href="/events/delete/${event.id}">Delete</a></td>
    			</c:if>
    			<c:if test="${event.host.id != currentUser.id }">
    				<c:if test="${event.users.contains(currentUser) }">
    					<td><a href="/cancel/${event_id}">Cancel</a></td>
    				</c:if>
    				<c:if test="${!event.users.contains(currentUser) }">
    				  <td><a href="/join/${event.id}">Join</a></td>
    				</c:if>
    			</c:if>
    			
    		</tr>
		</c:if>
    		</c:forEach>
    </table>
    <h3>Here are some of the events in other state:</h3>
    <table>
    		<tr>
    			<th>Name</th>
    			<th>Date</th>
    			<th>Location</th>
    			<th>State</th>
    			<th>Host</th>
    			<th>Action</th>
    		</tr>
		<c:forEach var="event" items="${events}">
		<c:if test="${currentUser.state != event.state }">
    		<tr>
    			<td><a href="/events/${event.id }">${event.name}</a></td>
    			<td><fmt:formatDate value="${event.date}" type="date" dateStyle="long"/></td>
    			<td><c:out value="${event.location}"/></td>
    			<td><c:out value="${event.state}"/></td>
    			<td><c:out value="${event.host.firstName}"/></td>
    			
    			<!--the host user can't join self event  -->
    			<c:if test="${event.host.id != currentUser.id }">
    				<c:if test="${event.users.contains(currentUser) }">
    					<td><a href="/cancel/${event.id}">Cancel</a></td>
    				</c:if>
    				<c:if test="${!event.users.contains(currentUser) }">
    					<td><a href="/join/${event.id}">Join</a></td>
    				</c:if>
    			</c:if>
    			<td><ul>
    				<c:forEach var="user" items="${event.users }">
    				<li>${user.firstName } ${user.lastName }</li>
    				</c:forEach></ul>
    			</td>
    		</tr>
		</c:if>
    		</c:forEach>
    
    
    
    
    </table>
   
  
    
    <hr>
    <h2>Create an Event</h2>
	  <div class="creatTable">
	  	<form:form method="POST" action="/event" modelAttribute="event">
			<p>
				<form:label path="name">Name:</form:label>
	            	<form:input path="name"/>
			</p>
			 <p>
	            <form:label path="date">Date:</form:label>
	            <form:input path="date" type="date"/>
	        </p>
			<p>
	        		<form:label path="location">Location:</form:label>
	        		<form:input path="location"/>
	        		<form:select  path="state" items="${states}"/>
		    </p>
		 	   <form:hidden path="host" value="${currentUser.id}"/>
		    <input type="submit" value="Create an Event"/>
		</form:form>
	  
	  </div>
	<hr>	   
  
    
    
    
   
    
    
</body>
</html>