<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 24.01.2018
  Time: 14:40
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
    <style>
        @import "/css/style2.css";
    </style>
</head>
<body>
<header>
    <c:import url="/jsp/admin/adminHeader.jsp" />
</header>
<form name="localeForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="new_dish"/>
    <input type="submit" value="<fmt:message key="label.newdish" bundle="${var}"/>" style="width:200px;height:50px"/>
</form>
<table width="100%" float="left" class="whiteback">
    <tr>
        <td><fmt:message key="label.dishname" bundle="${var}"/></td>
        <td><fmt:message key="label.dishtype" bundle="${var}"/></td>
        <td><fmt:message key="label.dishprice" bundle="${var}"/></td>
        <td><fmt:message key="label.dishcookingtime" bundle="${var}"/></td>
        <td><fmt:message key="label.dishamount" bundle="${var}"/></td>
        <td><fmt:message key="label.dishpictire" bundle="${var}"/></td>
    </tr>
    <c:forEach items="${dishes}" var="dish">
        <tr>
            <td>${dish.dishName}</td>
            <td>${dish.price}</td>
            <td>${dish.typeOfDish}</td>
            <td>${dish.cookingTime}</td>
            <td>${dish.maxNumberOfServings}</td>
            <td><img src="${dish.imagePath}" height="100" width="100" border="2px"></td>
            <td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="edit_dish"/>
                    <input type="hidden" name="choosenDish" value="${dish.dishName}"/>
                    <td><input type="submit" value="<fmt:message key="label.dishedit" bundle="${var}"/>" ></td>
                </form>
            </td>

        </tr>
    </c:forEach>
</table>
<form name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="logout" />
    <br/>
    <input type="submit" value=" <fmt:message key="label.logout" bundle="${var}"/>">
</form>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
