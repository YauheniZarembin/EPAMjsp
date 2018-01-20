<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<h3>Welcome</h3>
<hr/>
${user}, hello!
<hr/>
<c:forEach items="${dishes}" var="dish">
    <tr>
        <ul>${dish.dishName}</ul>
        <il>
            <img src="${dish.imagePath}" height="255" width="189" border="2px">
        </il>
    </tr>
</c:forEach>

<form name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="logout" />

    <br/>
    <input type="submit" value="Logout">
</form>
</body></html>