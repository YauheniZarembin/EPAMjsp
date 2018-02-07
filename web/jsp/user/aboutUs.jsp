
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>EPAM-cafe</title>
    <style>
        @import "/css/style.css";
    </style>
    <link rel="icon" href="/resource/image/epamcafe.jpg" type="images/jpg">
</head>
<body>
<header>
    <c:import url="/jsp/common/header.jsp" />
</header>
<div class="centerTable">
    <h1>EPAM-cafe</h1>
    <p>
        <fmt:message key="label.information1" bundle="${var}"/>
    </p>
    <p>
        <fmt:message key="label.information2" bundle="${var}"/>
    </p>
</div>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
