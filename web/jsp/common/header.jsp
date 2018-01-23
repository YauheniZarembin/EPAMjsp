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
            <td>
                <table class="whiteback" height="5">
                    <tr>
                        <td colspan="3" align="center"><b><h3>${user}, <fmt:message key="label.hello" bundle="${var}"/></h3></b></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="label.myprofile" bundle="${var}"/></td>
                        <td><fmt:message key="label.myorders" bundle="${var}"/></td>
                        <td><fmt:message key="label.logout" bundle="${var}"/></td>
                    </tr>
                </table>
            </td>
            <td><fmt:message key="label.headermenu" bundle="${var}"/></td>
            <td><fmt:message key="label.headercontacts" bundle="${var}"/></td>
            <td><fmt:message key="label.headeraboutus" bundle="${var}"/></td>
            <td><fmt:message key="label.headerreviews" bundle="${var}"/></td>
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
