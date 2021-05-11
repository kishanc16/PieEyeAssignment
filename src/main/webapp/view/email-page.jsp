<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My mail</title>
</head>
<body>
	<div align="center">
		<h1>Email</h1>
		<table border="1">
			<tr><td>From : </td><td>${email.from}</td></tr>
			<tr><td>Subject: </td><td>${email.subject}</td></tr>
			<tr> <td>Body: </td><td>${email.body}</td></tr>
		</table>
	</div>
</body>
</html>