<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 28.01.2018
  Time: 20:09
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
<div class="whiteback" style="width: 40%">
    <h1><fmt:message key="label.ordering" bundle="${var}"/></h1>
    <p style="color: green">
        <h2><fmt:message key="label.successorder" bundle="${var}"/></h2>
    </p>
    <p style="color: green">
        <b><fmt:message key="label.orderId" bundle="${var}"/></b>      ${orderID}
    </p>
    <p style="color: green">
        <b><fmt:message key="label.ordercost" bundle="${var}"/></b>      ${costResult}
    </p>
    <p style="color: green">
        <b><fmt:message key="label.orderdate" bundle="${var}"/></b>      ${dateTimeOrder}
    </p>
    <c:if test="${payment eq '1'}">
        <p style="color: green">
            <b><fmt:message key="label.orderpaymentcash" bundle="${var}"/></b>
        </p>
    </c:if>
    <c:if test="${payment eq '0'}">
        <p style="color: green">
            <b><fmt:message key="label.orderpaymentOK" bundle="${var}"/></b>
        </p>
    </c:if>
</div>
<c:import url="../jsp/common/footer.jsp" />
</body>
</html>