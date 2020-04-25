<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
</head>
<body>
<div style="align-content: stretch ; margin: 2%">
<h2>Add new product</h2>
<form method="post" action="${pageContext.request.contextPath}/addproduct">
    Product Name:
    <br/>
    <label>
        <input type="text" name="name">
    </label>
    <br/>
    Price:
    <br/>
    <label>
        <input type="number" name="price">
    </label>
    <br/>
    <button type="submit" style="width: 10%" class="btn btn-outline-primary">Add product</button>
</form>
<form action="${pageContext.request.contextPath}/">
    <button type="submit" style="width: 10%" class="btn btn-outline-primary">Main page</button>
</form>
</div>
</body>
</html>
