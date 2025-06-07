<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Welcome</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 30px;
        }

        h2 {
            color: #2c3e50;
            text-align: center;
        }

        .container {
            text-align: center;
            margin-top: 40px;
        }

        .container a {
            display: inline-block;
            margin: 10px;
            padding: 12px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .container a:hover {
            background-color: #2980b9;
        }
    </style>
</head>

<body>
<jsp:include page="navbar.jsp" />

<h2>Welcome, ${sessionScope.user.firstname}!</h2>

<div class="container">
    <h3>Select a Quiz Category:</h3>

    <c:forEach var="category" items="${categories}">
        <div>
            <span>${category.name}</span>
            <a href="quiz/start?categoryId=${category.categoryId}">Start Quiz</a>
        </div>
    </c:forEach>
</div>

