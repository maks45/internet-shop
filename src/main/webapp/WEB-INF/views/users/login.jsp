<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
</head>
<body>
<div style=" display: grid ; width: 100vw; height: 100vh;">
    <div style="align-self: center ; justify-self: center">
    <h2  style=" text-align: center; color: blue">Welcome!</h2>
    <p style=" align-self: center" >Please login or <a href="${pageContext.request.contextPath}/registration">register</a>:</p>
    <form method="post" style="display: inline-block ;line-height: initial;" action="${pageContext.request.contextPath}/login">
        <div class="form-group" >
            <label for="login">Login</label>
            <input type="text" minlength="4" class="form-control" name="login" id="login">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" name="password" id="password">
        </div>
        <p style=" color: red ; text-align: center;">${errorMsg} </p>
        <button type="submit" class="btn btn-primary">Login</button>
    </form>
    </div>
  </div>
</body>
</html>
