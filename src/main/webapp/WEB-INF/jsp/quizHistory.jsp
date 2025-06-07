<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Quiz History</title>
  <style>
    table {
      border-collapse: collapse;
      width: 90%;
      margin: auto;
    }
    th, td {
      border: 1px solid #ccc;
      padding: 10px;
    }
    th {
      background-color: #f2f2f2;
    }
    .passed {
      color: green;
      font-weight: bold;
    }
    .failed {
      color: red;
      font-weight: bold;
    }
  </style>
</head>
<body>

<h2 style="text-align:center;">Quiz History</h2>

<table>
  <tr>
    <th>Quiz ID</th>
    <th>Quiz Name</th>
    <th>Category</th>
    <th>Start Time</th>
    <th>End Time</th>
    <th>User</th>
    <th>Action</th>
  </tr>
  <c:forEach var="quiz" items="${quizList}">
    <tr>
      <td>${quiz.quizId}</td>
      <td>${quiz.name}</td>
      <td>${quiz.categoryName}</td>
      <td>${quiz.timeStart}</td>
      <td>${quiz.timeEnd}</td>
      <td>${quiz.userFullName}</td>
      <td><a href="/quiz/result?quizId=${quiz.quizId}">View Result</a></td>
    </tr>
  </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/home" method="get">
  <input type="submit" value="Back to Home" />
</form>


</body>
</html>
