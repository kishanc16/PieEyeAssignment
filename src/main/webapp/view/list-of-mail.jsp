<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Of Mails</title>
</head>
<body>

	<div align="center">
		<h1>List of All Mails</h1>
		<table border="">
		 <tr>
		 	<td>Mail Id</td>
		 	<td>From</td>
		 	<td>Subject</td>
		 	<td>Date</td>
		 	<td>Details</td>
		 </tr>
		 <c:forEach var="map" items="${listOfMail}">
		 <tr>
		 	<td>${map.getValue().mailId}</td>
		 	<td>${map.getValue().from}</td>
		 	<td>${map.getValue().subject}</td>
		 	<td>${map.getValue().receivedDate}</td>
		 	<td><a href="mail/${map.getValue().mailId}">Details</a></td>
		 </tr>
		 </c:forEach>
		 
		</table>
	</div>
</body>
</html>