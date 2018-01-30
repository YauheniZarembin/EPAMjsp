<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 24.01.2018
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <c:import url="/jsp/admin/adminHeader.jsp" />
</header>
<c:import url="/jsp/common/menu.jsp"/>
<input type="button" value="<fmt:message key="label.newDish" bundle="${var}"/>"  onClick='location.href="/jsp/admin/adminAddDish.jsp"' style="width:200px;height:50px"/>
<table width="100%" float="left" class="whiteback">
    <tr>
        <td><fmt:message key="label.dishName" bundle="${var}"/></td>
        <td><fmt:message key="label.dishPrice" bundle="${var}"/></td>
        <td><fmt:message key="label.dishType" bundle="${var}"/></td>
        <td><fmt:message key="label.dishPicture" bundle="${var}"/></td>
    </tr>
    <c:forEach items="${dishes}" var="dish">
        <tr>
            <td>${dish.dishName}</td>
            <td>${dish.price}</td>
            <td>${dish.typeOfDish}</td>
            <td><img src="${dish.imagePath}" height="100" width="100" border="2px"></td>
            <td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="edit_dish"/>
                    <input type="hidden" name="choosenDish" value="${dish.dishName}"/>
                    <td><input type="submit" value="<fmt:message key="label.dishEdit" bundle="${var}"/>" ></td>
                </form>
            </td>

        </tr>
    </c:forEach>
</table>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
