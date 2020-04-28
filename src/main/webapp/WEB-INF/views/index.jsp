<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
</head>
<body>
<div align= "left" style="align-content: stretch ; margin: 2%">
<h1>Internet shop:</h1>
<br/>
    <div style="width: auto">
        <form action="${pageContext.request.contextPath}/registration">
            <button  style="width: 10%" type="submit" class="btn btn-outline-primary">Registration</button>
        </form>

        <form action="${pageContext.request.contextPath}/products/edit">
            <button type="submit" style="width: 10%" class="btn btn-outline-primary">Edit products</button>
        </form>
        <form action="${pageContext.request.contextPath}/products/all">
            <button type="submit" style="width: 10%" class="btn btn-outline-primary">All products</button>
        </form>
        <form action="${pageContext.request.contextPath}/users/all">
            <button type="submit" style="width: 10%" class="btn btn-outline-primary">All users</button>
        </form>
        <form action="${pageContext.request.contextPath}/shoppingcart">
            <button type="submit" style="width: 10%" class="btn btn-outline-primary">Shopping cart</button>
        </form>
    </div>
</div>
</body>
</html>
