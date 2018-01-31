<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 23.01.2018
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title></title>
</head>
<body>
<table width="100%"  border="1" class="whiteback" >
    <tr>
        <td colspan="4" align="center"><b><h1><fmt:message key="label.dishBasket" bundle="${var}"/></h1></b></td>
    </tr>
    <c:if test="${not empty orders }">
    <tr align="center">
        <td><fmt:message key="label.dishName" bundle="${var}"/></td>
        <td><fmt:message key="label.dishPrice" bundle="${var}"/></td>
        <td><fmt:message key="label.numberOfServings" bundle="${var}"/></td>
    </tr>
    </c:if>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.key.dishName}</td>
            <td align="center">${order.key.price}</td>
            <td align="center">${order.value}</td>
            <td align="center">
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="delete_dish"/>
                    <input type="hidden"  name="dish" value="${order.key.dishName}" />
                    <input type="image" src="/resource/image/cross.jpg" height="20" width="20">
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty orders }">
        <tr>
            <td colspan="4" align="center">
                <fmt:message key="label.basketIsEmpty" bundle="${var}"/>
            </td>
        </tr>
    </c:if>

    <c:if test="${not empty orders }">
        <tr>
            <td colspan="4" align="center">
                <fmt:message key="label.orderPrice" bundle="${var}"/>  ${orderCost}
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <input type="button" value="<fmt:message key="label.ordering" bundle="${var}"/>"  onClick='location.href="/jsp/user/login/ordering.jsp"' />
            </td>
        </tr>
    </c:if>


</table>

</body>
</html>
