<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User orders</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
</head>
<body>
<div style="align-content: stretch ; margin: 2%">
<jsp:include page="../header.jsp" />
<h2>All orders for user - name: ${user.name}, login: ${user.login}, id: ${user.id}:</h2>
<table border="1" class="table">
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
</div>
</body>
</html>
