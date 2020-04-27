<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h2>Registration user</h2>
<form method="post" action="${pageContext.request.contextPath}/registration">
Name:
<br/>
<label>
    <input type="text" name="name" value="${name}">
</label>
<br/>
Login:
<br/>
<label>
    <input type="text" name="login" value="${login}">
</label>
<br/>
Password:
<br/>
<label>
    <input type="password" name="pwd">
</label>
<br/>
Confirm password:
<br/>
<label>
    <input type="password" name="pwd-confirm">
</label>
<h4 style="color: red">${msg}</h4>
<button type="submit">Register</button>
</form>
</body>
</html>
