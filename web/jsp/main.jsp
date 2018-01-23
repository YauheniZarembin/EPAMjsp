<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html><head>
    <title>EPAM-cafe</title>
    <style>
        @import "/css/style1.css";
    </style>
</head>
<body>
<header>
    <div>${user}, <fmt:message key="label.hello" bundle="${var}"/></div>
    <form name="localeForm" method="POST" action="/controller">
        <input type="hidden" name="pagePath" value="${pageContext.request.requestURL}" />
        <input type="hidden" name="command" value="locale" />
        <input type="submit" value=<fmt:message key="label.buttonlanguage" bundle="${var}"/> />
    </form>
</header>
<c:import url="../jsp/common/menu.jsp"/>
<hr/>

<table width="60%" class="whiteback">
    <tr>
        <td><fmt:message key="label.dishname" bundle="${var}"/></td>
        <td><fmt:message key="label.dishprice" bundle="${var}"/></td>
        <td><fmt:message key="label.dishcookingtime" bundle="${var}"/></td>
        <td><fmt:message key="label.dishmaxnumberofservings" bundle="${var}"/></td>
        <td><fmt:message key="label.dishpictire" bundle="${var}"/></td>
    </tr>
    <c:forEach items="${dishes}" var="dish">
        <tr>
            <td>${dish.dishName}</td>
            <td>${dish.price}</td>
            <td>${dish.cookingTime}</td>
            <td>${dish.maxNumberOfServings}</td>
            <td>
                <img src="${dish.imagePath}" height="100" width="100" border="2px">
            </td>
        </tr>
    </c:forEach>
</table>




<form name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="logout" />

    <br/>
    <input type="submit" value=" <fmt:message key="label.logout" bundle="${var}"/>">
</form>
<c:import url="../jsp/common/footer.jsp" />
</body></html>