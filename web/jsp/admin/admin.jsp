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
        @import "/css/style1.css";
    </style>
    <h1><fmt:message key="label.adminhead" bundle="${var}"/></h1>
</head>
<body>

</body>
</html>
