<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit Question</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fb;
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
        }

        form {
            width: 80%;
            margin: 0 auto;
            background-color: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
            color: #34495e;
        }

        select, textarea, input[type="text"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        textarea {
            height: 80px;
            resize: vertical;
        }

        .choice-block {
            display: block;
            margin-top: 15px;
            padding: 10px;
            background-color: #ecf0f1;
            border-radius: 6px;
        }


        .choice-block input[type="radio"] {
            margin-left: 10px;
        }

        .submit-btn {
            display: block;
            margin: 30px auto 0;
            background-color: #1abc9c;
            color: white;
            border: none;
            padding: 12px 24px;
            font-size: 16px;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .submit-btn:hover {
            background-color: #16a085;
        }
    </style>
</head>
<body>

<h2>Edit Question</h2>

<form action="${pageContext.request.contextPath}/admin/questions/edit" method="post">
    <input type="hidden" name="questionId" value="${question.questionId}"/>

    <label>Category:</label>
    <select name="categoryId">
        <c:forEach var="cat" items="${categoryList}">
            <option value="${cat.categoryId}" ${cat.categoryId == question.categoryId ? "selected" : ""}>${cat.name}</option>
        </c:forEach>
    </select>

    <label>Description:</label>
    <textarea name="description">${question.description}</textarea>

    <c:forEach var="choice" items="${question.choices}" varStatus="i">
        <div class="choice-block">
            <label>Choice ${i.index + 1}:</label>


            <div style="display: flex; align-items: center;">
                <input type="text" name="choices[${i.index}].description" value="${choice.description}" style="flex: 1;" />

                <c:if test="${choice.correct}">
                    <span style="margin-left: 10px; font-size: 18px;">&#10003;</span>
                </c:if>
            </div>

            <!-- 单选按钮，标记正确项 -->
            <label style="margin-top: 8px; display: inline-block;">
                <input type="radio" name="correct" value="${i.index}" <c:if test="${choice.correct}">checked</c:if> />
                Mark as Correct
            </label>

            <input type="hidden" name="choices[${i.index}].choiceId" value="${choice.choiceId}" />
            <input type="hidden" name="choices[${i.index}].questionId" value="${choice.questionId}" />
        </div>
    </c:forEach>


    <input type="submit" value="Save Changes" class="submit-btn" />
</form>
<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>

</body>
</html>

