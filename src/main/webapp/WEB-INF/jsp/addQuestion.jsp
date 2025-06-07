<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Question</title>
</head>
<body>
<h2>Add New Question</h2>

<form action="${pageContext.request.contextPath}/admin/questions/add" method="post">

    <!-- 选择题目分类 -->
    <label>Category:</label>
    <select name="categoryId" required>
        <c:forEach var="cat" items="${categoryList}">
            <option value="${cat.categoryId}">${cat.name}</option>
        </c:forEach>
    </select><br/><br/>

    <!-- 输入题干 -->
    <label>Question Description:</label><br/>
    <textarea name="description" rows="3" cols="50" required></textarea><br/><br/>

    <!-- 输入选项 -->
    <label>Choices:</label><br/>
    <c:forEach var="i" begin="0" end="3">
        Choice ${i + 1}:
        <input type="text" name="choices[${i}].description" required />
        <input type="radio" name="correct" value="${i}" ${i == 0 ? "checked" : ""}/> Correct<br/>
    </c:forEach>

    <br/>
    <input type="submit" value="Add Question" />
    <input type="reset" value="Reset" />
</form>
<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>

</body>
</html>
