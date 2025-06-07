<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz Result</title>
    <style>
        .correct { color: green; font-weight: bold; }
        .wrong { color: red; font-weight: bold; }
    </style>
</head>
<body>
<h2>Quiz Result</h2>

<p><strong>Quiz Name:</strong> ${quiz.name}</p>
<p><strong>User:</strong>
    <c:choose>
        <c:when test="${user.admin}">
            ${user.firstname} ${user.lastname}
        </c:when>
        <c:otherwise>
            You
        </c:otherwise>
    </c:choose>
</p>

<p><strong>Start Time:</strong> ${quiz.timeStart}</p>
<p><strong>End Time:</strong> ${quiz.timeEnd}</p>
<p><strong>Score:</strong> ${correctCount} / ${totalQuestions}</p>
<p><strong>Result:</strong>
    <c:choose>
        <c:when test="${passed}">
            Passed
        </c:when>
        <c:otherwise>
            Failed
        </c:otherwise>
    </c:choose>
</p>

<hr>
<h3>Question Details</h3>

<c:forEach var="q" items="${questionDetails}">
    <div style="margin-bottom: 20px;">
        <p><strong>Q:</strong> ${q.description}</p>
        <ul>
            <c:forEach var="c" items="${q.choices}">
                <li>
                    <c:choose>
                        <c:when test="${c.choiceId == q.userChoiceId}">
                            <c:choose>
                                <c:when test="${c.correct}">
                                    <span class="correct">You chose: ${c.description} (Correct Answer)</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="wrong">You chose: ${c.description} (Wrong Answer)</span>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            ${c.description}
                            <c:if test="${c.correct}">
                                <em> (Correct Answer)</em>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:forEach>

<c:if test="${user.admin}">
    <a href="/admin/quiz-results">← Back to Quiz List</a>
</c:if>
<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>
</body>
</html>

