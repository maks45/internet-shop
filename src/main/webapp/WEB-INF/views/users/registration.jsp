<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
</head>
<div align="left" style="align-content: stretch ; margin: 2%">
    <div style=" display: grid ; width: 100vw; height: 100vh;">
        <div style="align-self: center ; justify-self: center">
            <h2 style=" text-align: center; color: blue">Welcome!</h2>
            <p style=" align-self: center">Please register or <a href="${pageContext.request.contextPath}/login">login</a>:
            </p>
            <form method="post" style="display: inline-block ;line-height: initial;"
                  action="${pageContext.request.contextPath}/registration">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" required minlength="2" class="form-control" value="${name}" name="name" id="name">
                </div>
                <div class="form-group">
                    <label for="login">Login</label>
                    <input type="text" required minlength="4" class="form-control" value="${login}" name="login" id="login">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" required minlength="4" class="form-control" name="pwd" id="password">
                </div>
                <div class="form-group">
                    <label for="confirm-password">Confirm password</label>
                    <input type="password" required maxlength="4" class="form-control" name="pwd-confirm" id="confirm-password">
                </div>
                <p style=" color: red ; text-align: center;">${msg}</p>
                <button type="submit" class="btn btn-primary">Register</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
