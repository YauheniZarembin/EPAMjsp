<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 22.01.2018
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <table width="100%"  border="1" align="center" class="whiteback">
        <tr>
            <td colspan="5" align="center"><b><h1><fmt:message key="label.menu" bundle="${var}"/></h1></b></td>
        </tr>
        <tr  align="center">
            <td><b><fmt:message key="label.soup" bundle="${var}"/></b></td>
            <td><b><fmt:message key="label.sidedish" bundle="${var}"/></b></td>
            <td><b><fmt:message key="label.basic" bundle="${var}"/></b></td>
            <td><b><fmt:message key="label.dessert" bundle="${var}"/></b></td>
            <td><b><fmt:message key="label.drink" bundle="${var}"/></b></td>
        </tr>
    </table>
</body>
</html>
