<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 27.01.2018
  Time: 17:42
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
<div class="whiteback" style="width: 50%">
    <h1><fmt:message key="label.ordering" bundle="${var}"/></h1>
    <table width="80%"  border="1">
        <tr>
            <td colspan="4" align="center"><b><h1><fmt:message key="label.yourorder" bundle="${var}"/></h1></b></td>
        </tr>
            <tr align="center">
                <td><fmt:message key="label.dishname" bundle="${var}"/></td>
                <td><fmt:message key="label.dishprice" bundle="${var}"/></td>
                <td><fmt:message key="label.numberofservings" bundle="${var}"/></td>
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
                    <b><fmt:message key="label.orderprice" bundle="${var}"/></b>  ${orderCost}
                </td>
            </tr>
    </table>


    <form name="localeForm" method="POST" action="/controller">
        <input type="hidden" name="command" value="ordering"/>
        <h3><fmt:message key="label.paymentway" bundle="${var}"/></h3>
        <input type="radio" name="payment" value="1" checked> <fmt:message key="label.ordercash" bundle="${var}"/><br>
        <input type="radio" name="payment" value="0"> <fmt:message key="label.ordersite" bundle="${var}"/><br>
        <h3><fmt:message key="label.setdateoforder" bundle="${var}"/></h3>
        <fmt:message key="label.datecondition" bundle="${var}"/><br>
        <br>
        <fmt:message key="label.points" bundle="${var}"/><br>
        <fmt:message key="label.13points" bundle="${var}"/><br>
        <fmt:message key="label.47points" bundle="${var}"/><br>
        <br>
        <input type="datetime-local" name="dateTimeOrder" required oninvalid="this.setCustomValidity('<fmt:message key="label.incorrectdateortime" bundle="${var}"/>')" oninput="setCustomValidity('')"><br>
        ${Message}<br>
        <input type="submit" style="width: 200px; height: 50px;" value="<fmt:message key="label.makeorder" bundle="${var}"/>" >
    </form>
</div>

<c:import url="../jsp/common/footer.jsp" />
</body>
</html>