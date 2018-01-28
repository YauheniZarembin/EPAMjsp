<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 24.01.2018
  Time: 14:20
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
<table width="100%" float="left" class="whiteback">
    <tr>
        <td><h1><fmt:message key="label.userlogin" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.username" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userlastname" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userorders" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userpoints" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userban" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userrole" bundle="${var}"/></h1></td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.userName}</td>
            <td>${user.name}</td>
            <td>${user.lastname}</td>
            <td>${user.numberOfOrders}</td>
            <td>${user.loyaltyPoints}</td>
            <td>
                <c:if test="${user.isBan()}">
                        <fmt:message key="label.userbanyes" bundle="${var}"/>
                </c:if>
                <c:if test="${!user.isBan()}">
                    <fmt:message key="label.userbanno" bundle="${var}"/>
                </c:if>
            </td>
            <td>
                <c:if test="${user.isAdmin()}">
                    <fmt:message key="label.userroleadmin" bundle="${var}"/>
                </c:if>
                <c:if test="${!user.isAdmin()}">
                    <fmt:message key="label.userroleuser" bundle="${var}"/>
                </c:if>
            </td>
            <td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="edit_user"/>
                    <input type="hidden" name="choosenDish" value="${user.userName}"/>
            <td><input type="submit" value="<fmt:message key="label.dishedit" bundle="${var}"/>" ></td>
            </form>
            </td>

        </tr>
    </c:forEach>
</table>



<form name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="logout" />
    <input type="submit" value=" <fmt:message key="label.logout" bundle="${var}"/>">
</form>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>