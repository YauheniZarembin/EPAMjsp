<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 24.01.2018
  Time: 14:41
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
        @import "/css/style.css";
    </style>
    <link rel="icon" href="/resource/image/epamcafe.jpg" type="images/jpg">
</head>
<body>
    <header>
        <c:import url="/jsp/admin/adminHeader.jsp" />
    </header>

    <table width="100%" float="left" class="whiteback">
        <tr>
            <td><h1><fmt:message key="label.orderId" bundle="${var}"/></h1></td>
            <td><h1><fmt:message key="label.orderUser" bundle="${var}"/></h1></td>
            <td><h1><fmt:message key="label.orderDate" bundle="${var}"/></h1></td>
            <td><h1><fmt:message key="label.orderPayment" bundle="${var}"/></h1></td>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.userName}</td>
                <td>${order.dateOfReceiving}</td>
                <td>
                    <c:if test="${order.isCashPayment()}">
                        <fmt:message key="label.orderCash" bundle="${var}"/>
                    </c:if>
                    <c:if test="${!order.isCashPayment()}">
                        <fmt:message key="label.orderSite" bundle="${var}"/>
                    </c:if>
                </td>
                <td>
                    <form name="localeForm" method="POST" action="/controller">
                        <input type="hidden" name="command" value="delete_order"/>
                        <input type="hidden" name="choosenDish" value="${dish.dishName}"/>
                <td><input type="submit" value="<fmt:message key="label.delete" bundle="${var}"/>" ></td>
                </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form name="loginForm" method="POST" action="/controller">
        <input type="hidden" name="command" value="logout" />
        <br/>
        <input type="submit" value=" <fmt:message key="label.logOut" bundle="${var}"/>">
    </form>
    <c:import url="/jsp/common/footer.jsp" />

</body>
</html>
