
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
    <c:import url="/jsp/common/header.jsp" />
</header>
<c:if test="${empty userOrders}">
    <h1 class="whiteback"><fmt:message key="label.noOrders" bundle="${var}"/></h1>
</c:if>
<c:if test="${not empty userOrders}">
    <div class="whiteback">
<h1><fmt:message key="label.myOrders" bundle="${var}"/></h1>
<table width="100%" style="table-layout: fixed" border="1px">
    <tr align="center">
        <td><h3><fmt:message key="label.orderId" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderUser" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderDate" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderDishList" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderCost" bundle="${var}"/></h3></td>
        <td><h3><fmt:message key="label.orderPayment" bundle="${var}"/></h3></td>
    </tr>
    <c:forEach items="${userOrders}" var="userOrder">
        <tr align="center">
            <td>${userOrder.orderId}</td>
            <td>${userOrder.userName}</td>
            <td>${userOrder.dateOfReceiving}</td>
            <td>
                <table>
                    <c:forEach items="${userOrder.dishes}" var="orderDish">
                        <tr>
                            <td>${orderDish.key.dishName}</td>
                            <td><fmt:message key="label.amount" bundle="${var}"/>${orderDish.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>${userOrder.orderCost}</td>
            <td>
                <c:if test="${userOrder.isCashPayment()}">
                    <fmt:message key="label.orderCash" bundle="${var}"/>
                </c:if>
                <c:if test="${!userOrder.isCashPayment()}">
                    <fmt:message key="label.orderSite" bundle="${var}"/>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
    </div>
</c:if>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>