<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 25.01.2018
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>EPAM-cafe</title>
    <style>
        @import "/css/style2.css";
    </style>
</head>
<body>
<header>
    <c:import url="../jsp/common/header.jsp" />
</header>

<h1 class="h1order"><fmt:message key="label.myorders" bundle="${var}"/></h1>
<table width="60%" float="left" class="whiteback">
    <tr>
        <td><h1><fmt:message key="label.orderid" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.orderdishlist" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.orderdate" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.orderpayment" bundle="${var}"/></h1></td>
    </tr>
    <c:forEach items="${userOrders}" var="userOrder">
        <tr>
            <td>${userOrder.orderId}</td>
            <td>${userOrder.dateOfReceiving}</td>
            <td>
                <c:if test="${userOrder.isCashPayment()}">
                    <fmt:message key="label.ordercash" bundle="${var}"/>
                </c:if>
                <c:if test="${!userOrder.isCashPayment()}">
                    <fmt:message key="label.ordersite" bundle="${var}"/>
                </c:if>
            </td>
            <td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="delete_user_order"/>
                    <input type="hidden" name="choosenDish" value="${userOrder.orderId}"/>
                    <input type="submit" value="<fmt:message key="label.delete" bundle="${var}"/>">
            </form>
            </td>
        </tr>
    </c:forEach>
</table>
<c:import url="../jsp/common/footer.jsp" />
</body>
</html>