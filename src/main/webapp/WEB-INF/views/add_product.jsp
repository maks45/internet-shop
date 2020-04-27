<%--
  Created by IntelliJ IDEA.
  User: maks
  Date: 4/27/20
  Time: 7:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
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
    <button type="submit">Add product</button>
</form>
</body>
</html>
