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
    <link rel="icon" href="/resource/image/epamcafe.jpg" type="images/jpg">
</head>
<body>
<header>
    <c:import url="/jsp/common/header.jsp" />
</header>
<div class="centerTable">
    <h1><fmt:message key="label.ordering" bundle="${var}"/></h1>
    <table width="100%"  border="1">
        <tr>
            <td colspan="4" align="center"><b><h1><fmt:message key="label.yourOrder" bundle="${var}"/></h1></b></td>
        </tr>
            <tr align="center">
                <td><fmt:message key="label.dishName" bundle="${var}"/></td>
                <td><fmt:message key="label.dishPrice" bundle="${var}"/></td>
                <td><fmt:message key="label.numberOfServings" bundle="${var}"/></td>
            </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.key.dishName}</td>
                <td align="center">${order.key.price}</td>
                <td align="center">${order.value}</td>
                <td align="center"><img src="${order.key.imagePath}" height="70" width="70"></td>
            </tr>
        </c:forEach>
            <tr>
                <td colspan="4" align="center">
                    <b><fmt:message key="label.orderPrice" bundle="${var}"/></b>  ${orderCost}
                </td>
            </tr>
    </table>


    <form name="localeForm" method="POST" action="/controller">
        <input type="hidden" name="command" value="ordering"/>
        <h3><fmt:message key="label.paymentWay" bundle="${var}"/></h3>
        <input type="radio" name="payment" value="1" checked> <fmt:message key="label.orderCash" bundle="${var}"/><br>
        <input type="radio" name="payment" value="0"> <fmt:message key="label.orderSite" bundle="${var}"/><br>
        <h3><fmt:message key="label.setDateOfOrder" bundle="${var}"/></h3>
        <fmt:message key="label.dateCondition" bundle="${var}"/><br>
        <br>
        <fmt:message key="label.points" bundle="${var}"/><br>
        <fmt:message key="label.13points" bundle="${var}"/><br>
        <fmt:message key="label.47points" bundle="${var}"/><br>
        <fmt:message key="label.gift" bundle="${var}"/><br>
        <fmt:message key="label.fine" bundle="${var}"/><br>
        <br>
        <input type="datetime-local" name="dateTimeOrder" required oninvalid="this.setCustomValidity('<fmt:message key="label.incorrectDateOrTime" bundle="${var}"/>')" oninput="setCustomValidity('')"><br>
        ${Message}<br>
        <input type="submit" style="width: 200px; height: 50px;" value="<fmt:message key="label.makeOrder" bundle="${var}"/>" >
    </form>
</div>

<c:import url="/jsp/common/footer.jsp" />
</body>
</html>