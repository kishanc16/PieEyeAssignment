<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mail Login</title>
</head>
<body>
	<div align="center">
		<h1>Connect to the Mail</h1>
		
		<form:form action="mail" modelAttribute="connectionModel" method="GET">
			<table border="1">
				<tr>
					<td>Server:</td>
					<td><form:input path="server" size="20" /></td>
				</tr>
				<tr>
					<td>Port:</td>
					<td><form:input path="port" size="20" /></td>
				</tr>
				<tr>
					<td>Protocol:</td>
					<td><form:input path="protocol" size="20" /></td>
				</tr>
				<tr>
					<td>UserName:</td>
					<td><form:input path="username" size="20" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><form:password path="password" size="20" /></td>
				</tr>
				<tr>
					<td align="center" colspan="2"><form:button id="login" name="login">Login</form:button></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>