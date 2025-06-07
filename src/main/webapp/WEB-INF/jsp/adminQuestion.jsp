<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Question Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
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
            background-color: white;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
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

        form {
            display: inline;
        }

        input[type="submit"] {
            padding: 6px 12px;
            margin: 2px;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
        }

        input[value="Edit"] {
            background-color: #3498db;
            color: white;
        }

        input[value="Suspend"] {
            background-color: #e74c3c;
            color: white;
        }

        input[value="Activate"] {
            background-color: #2ecc71;
            color: white;
        }

        .add-button {
            display: block;
            width: 200px;
            margin: 30px auto;
            padding: 10px;
            text-align: center;
            background-color: #1abc9c;
            color: white;
            font-weight: bold;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .add-button:hover {
            background-color: #16a085;
        }
    </style>
</head>
<body>

<h2>Question Management</h2>

<table>
    <tr>
        <th>Category</th>
        <th>Description</th>
        <th>Choices</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>

    <c:forEach var="q" items="${questionList}">
        <tr>
            <td>${categoryMap[q.categoryId]}</td>

            <td>${q.description}</td>

            <td style="text-align: left;">
                <ul style="margin: 0; padding-left: 20px;">
                    <c:forEach var="choice" items="${q.choices}">
                        <li>${choice.description}</li>  <c:if test="${choice.correct}">&#10003;</c:if>

                    </c:forEach>
                </ul>
            </td>


            <td>
                <c:choose>
                    <c:when test="${q.active eq true}">
                        <span style="color:green;">Active</span>
                    </c:when>
                    <c:otherwise>
                        <span style="color:red;">Suspended</span>
                    </c:otherwise>
                </c:choose>
            </td>

            <td>
                <!-- Edit form -->
                <form action="${pageContext.request.contextPath}/admin/questions/edit" method="get">
                    <input type="hidden" name="id" value="${q.questionId}" />
                    <input type="submit" value="Edit" />
                </form>

                <!-- Toggle status form -->
                <form action="${pageContext.request.contextPath}/admin/questions/toggle" method="post">
                    <input type="hidden" name="id" value="${q.questionId}" />
                    <input type="hidden" name="active" value="${q.active eq true ? 'false' : 'true'}" />
                    <input type="submit" value="${q.active eq true ? 'Suspend' : 'Activate'}" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<a class="add-button" href="${pageContext.request.contextPath}/admin/questions/add">Add New Question</a>
<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>
</body>
</html>
