<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>
<head>
    <title>Admin Home</title>
</head>
<body>
<jsp:include page="navbar.jsp" />

<h2>Welcome, Admin!</h2>

<!-- 管理功能链接 -->
<ul>
    <li><a href="${pageContext.request.contextPath}/admin/users">User Management</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/questions">Question Management</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/contacts">Contact Us Management</a></li>

</ul>

<h3>System Stats</h3>
<ul>
    <li>Total Registered Users: <strong>${userCount}</strong></li>
    <li>Most Popular Category: <strong>${popularCategory}</strong></li>
</ul>

</body>
</html>

