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
<table width="100%"  border="1" class="whiteback" align="center" >
    <tr>
        <td colspan="3" align="center"><b><h1><fmt:message key="label.dishbasket" bundle="${var}"/></h1></b></td>
    </tr>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.dishName}</td>
            <td>${order.price}</td>
            <td align="centre">
                <input type="image" src="/resource/image/cross.jpg" height="10" width="10" alt="ОК">
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty orders }">
        <tr>
            <td colspan="3" align="center">
                <fmt:message key="label.basketisempty" bundle="${var}"/>
            </td>
        </tr>
    </c:if>

    <c:if test="${not empty orders }">
        <tr>
            <td colspan="3" align="center">
                <fmt:message key="label.orderprice" bundle="${var}"/>
            </td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <input type="submit" value="<fmt:message key="label.makeorder" bundle="${var}"/>">
            </td>
        </tr>
    </c:if>


</table>

</body>
</html>
