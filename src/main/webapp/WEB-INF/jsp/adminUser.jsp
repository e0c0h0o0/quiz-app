<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>User Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ecf0f1;
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
        }

        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 12px 16px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #2c3e50;
            color: white;
        }

        tr:hover {
            background-color: #f2f2f2;
        }

        input[type="submit"] {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
        }

        input[type="submit"][value="Suspend"] {
            background-color: #e74c3c;
            color: white;
        }

        input[type="submit"][value="Activate"] {
            background-color: #2ecc71;
            color: white;
        }

        .pagination {
            text-align: center;
            margin-top: 20px;
        }

        .pagination a, .pagination strong {
            margin: 0 4px;
            padding: 6px 10px;
            text-decoration: none;
            color: #2c3e50;
            border-radius: 4px;
        }

        .pagination a:hover {
            background-color: #1abc9c;
            color: white;
        }

        .pagination strong {
            background-color: #2c3e50;
            color: white;
        }
    </style>
</head>
<body>

<h2>User Management</h2>

<table>
    <tr>
        <th>User ID</th>
        <th>Email</th>
        <th>Full Name</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.email}</td>
            <td>${user.firstname} ${user.lastname}</td>
            <td>
                <c:choose>
                    <c:when test="${user.active}">Active</c:when>
                    <c:otherwise>Suspended</c:otherwise>
                </c:choose>
            </td>
            <td>
                <form method="post" action="users/status">
                    <input type="hidden" name="userId" value="${user.userId}" />
                    <input type="hidden" name="isActive" value="${user.active}" />
                    <input type="hidden" name="page" value="${currentPage}" />
                    <c:choose>
                        <c:when test="${user.active}">
                            <input type="submit" value="Suspend" />
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Activate" />
                        </c:otherwise>
                    </c:choose>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<!-- 分页 -->
<div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
            <c:when test="${i == currentPage}">
                <strong>[${i}]</strong>
            </c:when>
            <c:otherwise>
                <a href="?page=${i}">[${i}]</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>
<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>

</body>
</html>
