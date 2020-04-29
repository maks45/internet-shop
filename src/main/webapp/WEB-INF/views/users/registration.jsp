<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
</head>
<div align= "left" style="align-content: stretch ; margin: 2%">
    <jsp:include page="../header.jsp" />
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
    <br/>
    <h7 style="color: red">${msg}</h7>
    <br/>
    <button type="submit" style="width: 10%" class="btn btn-outline-primary">Register</button>
</form>
</div>
</body>
</html>
