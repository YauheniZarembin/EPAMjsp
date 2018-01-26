<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html><head>
    <title>EPAM-cafe</title>
    <style>
        @import "/css/style2.css";
    </style>
</head>
<body>
<header>
    <c:import url="../jsp/common/header.jsp" />
</header>
<c:import url="../jsp/common/menu.jsp"/>
<hr/>
<table width="100%">
    <tr>
        <td valign="top" width="75%">
    <table width="60%" float="left" class="whiteback">
    <tr>
        <td><fmt:message key="label.dishname" bundle="${var}"/></td>
        <td><fmt:message key="label.dishprice" bundle="${var}"/></td>
        <td><fmt:message key="label.dishpictire" bundle="${var}"/></td>
    </tr>
    <c:forEach items="${dishes}" var="dish">
        <tr>
            <td>${dish.dishName}</td>
            <td>${dish.price}</td>
            <td><img src="${dish.imagePath}" height="100" width="100" border="2px"></td>

            <c:if test="${not empty user}">
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="add_Dish"/>
                <input type="hidden" name="choosenDish" value="${dish.dishName}"/>
                <td><input type="submit" value="<fmt:message key="label.dishorder" bundle="${var}"/>" ></td>
            </form>
            </c:if>
        </tr>
    </c:forEach>
    </table>
    </td>
        <c:if test="${not empty user}">
            <td valign="top" width="25%">
                <c:import url="../jsp/common/order.jsp" />
            </td>
        </c:if>
    </tr>
</table>
<c:import url="../jsp/common/footer.jsp" />
</body>
</html>