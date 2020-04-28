<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User orders</title>
</head>
<body>
<h1>All orders for user with id #${userId}:</h1>
<table border="1">
    <tr>
        <th>Order ID</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <a href= ${pageContext.request.contextPath}/orders/complete?order_id=${order.orderId}>
                    <c:out value="${order.orderId}"/>
                </a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/delete?order_id="${order.orderId}>Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
