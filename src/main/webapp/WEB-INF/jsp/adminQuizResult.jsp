<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz Results</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
        }

        form {
            text-align: center;
            margin-bottom: 20px;
        }

        select, button {
            padding: 8px 12px;
            margin: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        button {
            background-color: #3498db;
            color: white;
            cursor: pointer;
            border: none;
        }

        button:hover {
            background-color: #2980b9;
        }

        table {
            margin: 0 auto;
            width: 90%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 12px 15px;
            text-align: center;
            border-bottom: 1px solid #eee;
        }

        th {
            background-color: #2c3e50;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        a {
            color: #3498db;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        .pagination {
            margin-top: 20px;
            text-align: center;
        }

        .pagination a {
            margin: 0 5px;
            padding: 6px 10px;
            border: 1px solid #3498db;
            color: #3498db;
            border-radius: 4px;
        }

        .pagination a:hover {
            background-color: #3498db;
            color: white;
        }

        .pagination .current {
            background-color: #3498db;
            color: white;
            font-weight: bold;
        }
    </style>
</head>
<body>

<h2>Quiz Results</h2>

<form method="get">
    User:
    <select name="userId">
        <option value="">All</option>
        <c:forEach var="user" items="${users}">
            <option value="${user.userId}" ${user.userId == selectedUserId ? 'selected' : ''}>
                    ${user.firstname} ${user.lastname}
            </option>
        </c:forEach>
    </select>

    Category:
    <select name="categoryId">
        <option value="">All</option>
        <c:forEach var="cat" items="${categories}">
            <option value="${cat.categoryId}" ${cat.categoryId == selectedCategoryId ? 'selected' : ''}>
                    ${cat.name}
            </option>
        </c:forEach>
    </select>

    <button type="submit">Filter</button>
</form>

<table>
    <tr>
        <th>
            <a href="?page=${currentPage}&userId=${selectedUserId}&categoryId=${selectedCategoryId}&sortBy=name&order=${sortBy == 'name' && order == 'asc' ? 'desc' : 'asc'}">
                User
            </a>
        </th>
        <th>
            <a href="?page=${currentPage}&userId=${selectedUserId}&categoryId=${selectedCategoryId}&sortBy=category&order=${sortBy == 'category' && order == 'asc' ? 'desc' : 'asc'}">
                Category
            </a>
        </th>
        <th>
            <a href="?page=${currentPage}&userId=${selectedUserId}&categoryId=${selectedCategoryId}&sortBy=time&order=${sortBy == 'time' && order == 'asc' ? 'desc' : 'asc'}">
                Time
            </a>
        </th>
        <th>Score</th>

    </tr>

    <c:forEach var="quiz" items="${quizList}">
        <tr>
            <td>${quiz.userFullName}</td>
            <td>${quiz.categoryName}</td>
            <td>${quiz.timeStart}</td>
<%--            <td>${quiz.score} / ${quiz.total}</td><td>${quiz.score} / ${quiz.total}</td>--%>
            <td><a href="/admin/quiz-results/result?id=${quiz.quizId}">View</a></td>
        </tr>
    </c:forEach>
</table>

<c:if test="${totalPages > 1}">
    <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="current">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="?page=${i}&userId=${selectedUserId}&categoryId=${selectedCategoryId}&sortBy=${sortBy}&order=${order}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>



</body>
</html>
