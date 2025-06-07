<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<style>
    nav {
        background-color: #2c3e50;
        padding: 10px 20px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    nav ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        display: flex;
        gap: 15px;
    }

    nav ul li {
        display: inline;
    }

    nav ul li a {
        text-decoration: none;
        color: white;
        font-weight: bold;
        padding: 8px 12px;
        border-radius: 4px;
        transition: background-color 0.3s ease;
    }

    nav ul li a:hover {
        background-color: #1abc9c;
        color: #fff;
    }
</style>

<nav>
    <ul>
        <c:if test="${not empty sessionScope.user}">
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        </c:if>

        <c:if test="${not empty sessionScope.user}">
            <c:choose>
                <c:when test="${sessionScope.user.admin}">
                    <li><a href="${pageContext.request.contextPath}/admin/quiz-results">Quiz Result Management</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/quiz/history">Quiz Result</a></li>
                </c:otherwise>
            </c:choose>
        </c:if>

        <c:if test="${sessionScope.hasOngoingQuiz == true}">
            <li><a href="${pageContext.request.contextPath}/quiz/current">Quiz</a></li>
        </c:if>


        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
            </c:otherwise>
        </c:choose>

        <c:if test="${empty sessionScope.user}">
            <li><a href="${pageContext.request.contextPath}/register">Register</a></li>
        </c:if>

        <c:if test="${not empty sessionScope.user and not sessionScope.user.admin}">
            <li><a href="${pageContext.request.contextPath}/contact">Contact Us</a></li>
        </c:if>

    </ul>
</nav>


