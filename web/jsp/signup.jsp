<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html><head>
    <title>Welcome</title>
    <style>
        @import "/css/style2.css";
    </style>
<body>

<header>
    <c:import url="../jsp/common/header.jsp" />
</header>

<form  name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="signup" />
    <table align="center" class="whiteback">
        <tr>
            <td colspan="3" align="center"><b><h1><fmt:message key="label.signuphead" bundle="${var}"/></h1></b></td>
        </tr>

        <tr>
            <td><fmt:message key="label.login" bundle="${var}"/></td>
            <td><input type="text" name="username" value=""></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.password" bundle="${var}"/></td>
            <td><input type="password" name="password" value=""></td>
            <td ><fmt:message key="label.passwordcondition" bundle="${var}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="label.singupname" bundle="${var}"/></td>
            <td><input type="text" name="name" value=""></td>
        </tr>
        <tr>
            <td><fmt:message key="label.signuplastname" bundle="${var}"/></td>
            <td><input type="text" name="lastname" value=""></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.signupemail" bundle="${var}"/></td>
            <td><input type="text" name="email" value=""></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.signupcardnumber" bundle="${var}"/></td>
            <td><input type="text" name="card number" value=""></td>
            <td ><fmt:message key="label.cardnumbercondition" bundle="${var}"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center" >${errorMessage}</td>
        </tr>
        <br/>
    </table>
    <input type="submit" value="<fmt:message key="label.buttonsignup" bundle="${var}"/>">
</form>
<input type="button" value="<fmt:message key="label.back" bundle="${var}"/>" onClick='location.href="/jsp/login.jsp"'>
<hr/>
<c:import url="../jsp/common/footer.jsp" />
</body></html>