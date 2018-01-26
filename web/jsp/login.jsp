<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>EPAM-cafe</title>
    <style>
        @import "/css/style2.css";
    </style>
</head>
<div class="wrapper">
<body>
<header>
    <c:import url="../jsp/common/header.jsp" />
</header>
<form name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="login"/>
    <table align="center" class="whiteback">
        <tr>
            <td colspan="2" align="center"><b><h1><fmt:message key="label.enter" bundle="${var}"/></h1></b></td>
        </tr>
        <tr><td><fmt:message key="label.login" bundle="${var}"/><span class="req">*</span></td></tr>
        <tr><td><input type="text" name="login" value=""/></td></tr>
        <tr><td><br/><fmt:message key="label.password" bundle="${var}"/><span class="req">*</span></td></tr>
        <tr><td><input type="password" name="password" value=""/></td></tr>
        <tr><td>${Message}</td> </tr>
        <tr><td>${wrongAction}</td></tr>
        <tr><td>${nullPage}</td></tr>
        <tr><td></td></tr>
        <tr>
            <td>
                <input type="submit" value="<fmt:message key="label.buttonlogin" bundle="${var}"/>"/>
            </td>
            <td>
                <input type="button" value="<fmt:message key="label.buttonsignup" bundle="${var}"/>"  onClick='location.href="/jsp/signup.jsp"' />
            </td>
        </tr>
    </table>
</form>
<hr/>
<c:import url="../jsp/common/footer.jsp"/>
</body>
    </div>
</html>