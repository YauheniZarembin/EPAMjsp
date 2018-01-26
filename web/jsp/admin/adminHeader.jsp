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
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>Title</title>
    <style>
        @import "/css/style2.css";
    </style>
</head>
<body>
<header>
    <table width="100%" class="whiteheader">
        <tr>
            <td colspan="3" align="left"><b><h1><fmt:message key="label.pageadmin" bundle="${var}"/></h1></b></td>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_menu"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm'].submit();"><fmt:message key="label.headermenu" bundle="${var}"/></a></td>
            </form>
            <form name="localeForm1" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_users"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm1'].submit();"><fmt:message key="label.users" bundle="${var}"/></a></td>
            </form>
            <form name="localeForm2" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_orders"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm2'].submit();"><fmt:message key="label.orders" bundle="${var}"/></a></td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_rewievs"/>
                <td><input type="submit" value="<fmt:message key="label.headerreviews" bundle="${var}"/>"/></td>
            </form>
            <td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="pagePath" value="${pageContext.request.requestURL}" />
                    <input type="hidden" name="command" value="locale"/>
                    <input type="image" src="<fmt:message key="label.picturelocale" bundle="${var}"/>" height="20" width="30" alt="<fmt:message key="label.buttonlanguage" bundle="${var}"/>">
                </form>
            </td>
        </tr>
    </table>
</header>
</body>
</html>
