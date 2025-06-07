<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head><title>Admin Contact List</title></head>
<body>
<h2>All Contact Us Messages</h2>
<table border="1">
    <tr>
        <th>Subject</th>
        <th>Email</th>
        <th>Time</th>
        <th>Message</th>
        <th>Action</th>
    </tr>
    <c:forEach var="c" items="${contacts}">
        <tr>
            <td>${c.subject}</td>
            <td>${c.email}</td>
            <td>${c.time}</td>
            <td>
                <c:choose>
                    <c:when test="${fn:length(c.message) > 30}">
                        ${fn:substring(c.message, 0, 30)}...
                    </c:when>
                    <c:otherwise>
                        ${c.message}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <form action="contacts/view" method="get">
                    <input type="hidden" name="id" value="${c.contactId}"/>
                    <input type="submit" value="View"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>
</body>
</html>
