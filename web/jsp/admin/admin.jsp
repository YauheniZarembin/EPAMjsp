<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 22.01.2018
  Time: 5:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>Admin page</title>
    <style>
        @import "/css/style.css";
    </style>
</head>
<body>
    <header>
        <c:import url="/jsp/admin/adminHeader.jsp" />
    </header>
    <form name="loginForm" method="POST" action="/controller">
        <input type="hidden" name="command" value="logout" />
        <input type="submit" value=" <fmt:message key="label.logout" bundle="${var}"/>">
    </form>
    <c:import url="/jsp/common/footer.jsp" />
</body>
</html>
