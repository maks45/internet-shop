<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<h1>All orders:</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>User Login</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
            <a href="${pageContext.request.contextPath}/order?order_id=${order.id}">
                <c:out value="${order.id}"/>
            </a>
            </td>
            <td>
                <c:out value="${order.user.login}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/addtoshoppingcart?product_id=${product.id}">
                    Add to shopping cart
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="${pageContext.request.contextPath}/shoppingcart">Go to shopping cart</a>
<br/>
<a href="${pageContext.request.contextPath}/">Main page</a>
</body>
