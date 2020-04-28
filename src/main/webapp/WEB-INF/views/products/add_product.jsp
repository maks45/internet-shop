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
<br/>
<a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
