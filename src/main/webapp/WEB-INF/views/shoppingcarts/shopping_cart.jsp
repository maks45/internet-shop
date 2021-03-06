<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
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
<h2>Shopping cart:</h2>
<table border="1" class="table">
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
                <a href="${pageContext.request.contextPath}/users/shopping-cart/remove?product_id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<p>
    <h2 style="color: red">${msg}</h2>
</p>
<form action="${pageContext.request.contextPath}/orders/complete">
    <button type="submit" style="width: 10%" class="btn btn-outline-primary">Complete order</button>
</form>
</div>
</body>
</html>
