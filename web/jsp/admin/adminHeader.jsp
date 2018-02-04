<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 24.01.2018
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
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
    <table width="100%" class="whiteheader">
        <tr>
            <td colspan="2" align="center"><ctg:role user="${user}"/></td>
            <form name="localeForm23" method="POST" action="/controller">
                <input type="hidden" name="command" value="logout"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm23'].submit();"><fmt:message key="label.logOut" bundle="${var}"/></a></td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_menu"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm'].submit();"><fmt:message key="label.headerMenu" bundle="${var}"/></a></td>
            </form>
            <form name="localeForm1" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_users"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm1'].submit();"><fmt:message key="label.users" bundle="${var}"/></a></td>
            </form>
            <form name="localeFormOrder" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_order"/>
                <input type="hidden" name="ordersType" value="today"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeFormOrder'].submit();"><fmt:message key="label.orders" bundle="${var}"/></a></td>
            </form>
            <form name="localeForm9" method="POST" action="/controller">
                <input type="hidden" name="command" value="reviews_command"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm9'].submit();"><fmt:message key="label.headerReviews" bundle="${var}"/></a> </td>
            </form>
            <td>
                <table class="whiteheader" align="center">
                    <tr align="center">
                        <form name="localeForm" method="POST" action="/controller">
                            <input type="hidden" name="pagePath" value="${pageContext.request.requestURL}" />
                            <input type="hidden" name="command" value="locale"/>
                            <td><input type="image" src="<fmt:message key="label.pictureLocale" bundle="${var}"/>" height="20" width="30" alt="<fmt:message key="label.buttonLanguage" bundle="${var}"/>"> </td>
                        </form>
                    </tr>
                    <tr><td><ctg:infoTimeTag/></td></tr>
                </table>
            </td>
        </tr>
    </table>
</header>
</body>
</html>
