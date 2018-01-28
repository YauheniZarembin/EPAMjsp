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
        @import "/css/style.css";
    </style>
</head>
<body>
<header>
    <c:import url="../jsp/common/header.jsp" />
</header>
<c:if test="${empty userOrders}">
    <h1 class="whiteback"><fmt:message key="label.noOrders" bundle="${var}"/></h1>
</c:if>
<c:if test="${not empty userOrders}">
    <div class="whiteback">
<h1><fmt:message key="label.myorders" bundle="${var}"/></h1>
<table width="100%" style="table-layout: fixed" border="1px">
    <tr align="center">
        <td><h3><fmt:message key="label.orderid" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderuser" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderdate" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderdishlist" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.ordercost" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderpayment" bundle="${var}"/></h3></td>
    </tr>
    <c:forEach items="${userOrders}" var="userOrder">
        <tr align="center">
            <td>${userOrder.orderId}</td>
            <td>${userOrder.userName}</td>
            <td>${userOrder.dateOfReceiving}</td>
            <td>
                <table style="table-layout: fixed">
                    <c:forEach items="${userOrder.dishes}" var="orderDish">
                        <tr>
                            <td>${orderDish.key.dishName}</td>
                            <td><fmt:message key="label.amount" bundle="${var}"/>  ${orderDish.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>${userOrder.orderCost}</td>
            <td>
                <c:if test="${userOrder.isCashPayment()}">
                    <fmt:message key="label.ordercash" bundle="${var}"/>
                </c:if>
                <c:if test="${!userOrder.isCashPayment()}">
                    <fmt:message key="label.ordersite" bundle="${var}"/>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
    </div>
</c:if>
<c:import url="../jsp/common/footer.jsp" />
</body>
</html>