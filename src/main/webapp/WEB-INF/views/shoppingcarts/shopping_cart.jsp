<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<h1>Shopping cart:</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shoppingcarts/removeproduct?product_id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p>
    <h2 style="color: red">${msg}</h2>
</p>
<p>
    <a href="${pageContext.request.contextPath}/orders/complete">Complete order</a>
</p>
<br/>
<a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
