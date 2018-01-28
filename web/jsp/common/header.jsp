<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 24.01.2018
  Time: 0:15
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
</head>
<body>
<header>
    <table width="100%" class="whiteheader">
        <tr  align="center">
            <c:if test="${not empty user}">
            <td>
                <table class="whiteback" height="5" border="5">
                    <tr>
                        <td colspan="3" align="center"><b><h3>${user.name} ${user.lastname}, <fmt:message key="label.hello" bundle="${var}"/></h3></b></td>
                    </tr>
                    <tr>
                        <td><a href='/jsp/myProfile.jsp' style="color: black"><fmt:message key="label.myprofile" bundle="${var}"/></a></td>
                        <form name="localeFormOrders" method="POST" action="/controller">
                            <input type="hidden" name="command" value="user_orders"/>
                            <td><a href='#' style="color:black" onClick="document.forms['localeFormOrders'].submit();"><fmt:message key="label.myorders" bundle="${var}"/></a></td>
                        </form>
                        <form name="localeFormOut" method="POST" action="/controller">
                            <input type="hidden" name="command" value="logout"/>
                            <td><a href='#' style="color:black" onClick="document.forms['localeFormOut'].submit();"><fmt:message key="label.logout" bundle="${var}"/></a></td>
                        </form>
                    </tr>
                </table>
            </td>
            </c:if>
            <c:if test="${empty user}">
                <td align="left"><b><h2><fmt:message key="label.epamcafewelcome" bundle="${var}"/></h2></b></td>
            </c:if>
            <c:if test="${empty user}">
                <td><a href='/jsp/login.jsp' style="color: white"><fmt:message key="label.entersite" bundle="${var}"/></a></td>
            </c:if>
            <td><a href='/jsp/main.jsp' style="color: white"><fmt:message key="label.headermenu" bundle="${var}"/></a></td>
            <td><a href='/jsp/contacts.jsp' style="color: white"><fmt:message key="label.headercontacts" bundle="${var}"/></a></td>
            <td><a href='/jsp/aboutUs.jsp' style="color: white"><fmt:message key="label.headeraboutus" bundle="${var}"/></a></td>
            <form name="localeForm9" method="POST" action="/controller">
                <input type="hidden" name="command" value="reviews_command"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm9'].submit();"><fmt:message key="label.headerreviews" bundle="${var}"/></a> </td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="pagePath" value="${pageContext.request.requestURL}" />
                <input type="hidden" name="command" value="locale"/>
                <td><input type="image" src="<fmt:message key="label.picturelocale" bundle="${var}"/>" height="20" width="30" alt="<fmt:message key="label.buttonlanguage" bundle="${var}"/>"> </td>
            </form>
        </tr>
    </table>
</header>
</body>
</html>
