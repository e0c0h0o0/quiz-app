<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Contact Us</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            padding: 20px;
            color: #333;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
        }

        .success-message {
            color: green;
            text-align: center;
            font-weight: bold;
            margin-bottom: 15px;
        }

        form {
            max-width: 500px;
            margin: 20px auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="email"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"],
        .back-button {
            margin-top: 15px;
            width: 100%;
            padding: 10px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover,
        .back-button:hover {
            background-color: #2980b9;
        }

        .button-wrapper {
            margin-top: 10px;
        }
    </style>
</head>

<body>
<jsp:include page="navbar.jsp" />

<h2>Contact Us</h2>

<c:if test="${not empty success}">
    <p class="success-message">${success}</p>
</c:if>

<form action="${pageContext.request.contextPath}/contact" method="post">
    <label for="subject">Subject:</label>
    <input type="text" name="subject" id="subject" required />

    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required />

    <label for="message">Message:</label>
    <textarea name="message" id="message" rows="5" required></textarea>

    <input type="submit" value="Send Message" />
</form>

<form action="${pageContext.request.contextPath}/home" method="get">
    <div class="button-wrapper">
        <input type="submit" value="Back to Home" class="back-button" />
    </div>
</form>

</body>
</html>
