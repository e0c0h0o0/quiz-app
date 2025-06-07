<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Contact Detail</title></head>
<body>
<h2>Contact Message Detail</h2>
<p><strong>Subject:</strong> ${contact.subject}</p>
<p><strong>Email:</strong> ${contact.email}</p>
<p><strong>Time:</strong> ${contact.time}</p>
<p><strong>Message:</strong></p>
<p>${contact.message}</p>
<br/>
<a href="/admin/contacts">Back to List</a>
<form action="${pageContext.request.contextPath}/home" method="get">
    <input type="submit" value="Back to Home" />
</form>

</body>
</html>
