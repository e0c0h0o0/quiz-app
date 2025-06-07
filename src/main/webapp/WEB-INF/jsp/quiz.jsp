<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head><title>Quiz</title></head>
<body>

<jsp:include page="navbar.jsp" />

<h2>Quiz: ${quiz.name}</h2>

<form action="/quiz/submit" method="post">
    <input type="hidden" name="quizId" value="${quiz.quizId}" />

    <c:forEach var="question" items="${questions}" varStatus="loop">
        <div>
            <p>Q${loop.index + 1}: ${question.description}</p>
            <c:forEach var="choice" items="${question.choices}">
                <label>
                    <input type="radio" name="question_${question.questionId}" value="${choice.choiceId}" />
                        ${choice.description}
                </label><br/>
            </c:forEach>
        </div>
        <br/>
    </c:forEach>

    <input type="submit" value="Submit Quiz" />
</form>
<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>

</body>
</html>
