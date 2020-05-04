<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<header class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Noname shop</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
        </ul>
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/users/shopping-cart" role="button">Shopping cart</a>
    </div>
</header>
</body>
</html>
