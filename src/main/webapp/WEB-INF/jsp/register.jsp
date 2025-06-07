<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Register</title>
</head>

<body>
<!-- 导航栏 -->
<jsp:include page="navbar.jsp" />

<h2>Register</h2>

<!-- 可选：注册失败提示 -->
<c:if test="${not empty errorMessage}">
    <p style="color:red;">${errorMessage}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/register">
    <div>
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required />
    </div>
    <div>
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />
    </div>
    <div>
        <label for="firstname">First Name</label>
        <input type="text" id="firstname" name="firstname" required />
    </div>
    <div>
        <label for="lastname">Last Name</label>
        <input type="text" id="lastname" name="lastname" required />
    </div>
    <div>
        <button type="submit">Register</button>
    </div>
</form>

<p>
    Already have an account?
    <a href="${pageContext.request.contextPath}/login">Login here</a>
</p>
</body>
</html>
