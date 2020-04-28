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
<div class="container">
<h1>Internet shop:</h1>
<br/>
        <form action="${pageContext.request.contextPath}/registration">
            <button style="align-items: center" type="submit" class="btn btn-outline-primary">Registration</button>
        </form>

        <form action="${pageContext.request.contextPath}/addproduct">
            <button type="submit" class="btn btn-outline-primary">Add product</button>
        </form>
        <form action="${pageContext.request.contextPath}/products/all">
            <button type="submit" class="btn btn-outline-primary">All products</button>
        </form>
        <form action="${pageContext.request.contextPath}/users/all">
            <button type="submit" class="btn btn-outline-primary">All users</button>
        </form>
        <form action="${pageContext.request.contextPath}/shoppingcart">
            <button type="submit" class="btn btn-outline-primary">Shopping cart</button>
        </form>
</div>
</body>
</html>
