<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html><head>
    <title>EPAM-cafe</title>
    <style>
        @import "/css/style.css";
    </style>
    <link rel="icon" href="/resource/image/epamcafe.jpg" type="images/jpg">
<body>

<header>
    <c:import url="../jsp/common/header.jsp" />
</header>

<form  name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="signup" />
    <table align="center" class="whiteback">
        <tr>
            <td colspan="3" align="center"><b><h1><fmt:message key="label.signUpHead" bundle="${var}"/></h1></b></td>
        </tr>

        <tr>
            <td><fmt:message key="label.logIn" bundle="${var}"/></td>
            <td><input type="text" name="username" value=""></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.password" bundle="${var}"/></td>
            <td><input type="password" name="password" value=""></td>
            <td ><fmt:message key="label.passwordCondition" bundle="${var}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="label.singUpName" bundle="${var}"/></td>
            <td><input type="text" name="name" value=""></td>
        </tr>
        <tr>
            <td><fmt:message key="label.signUpLastname" bundle="${var}"/></td>
            <td><input type="text" name="lastname" value=""></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.signUpEmail" bundle="${var}"/></td>
            <td><input type="text" name="email" value=""></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.signUpCardNumber" bundle="${var}"/></td>
            <td><input type="text" name="card number" value=""></td>
            <td ><fmt:message key="label.cardNumberCondition" bundle="${var}"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center" >${errorMessage}</td>
        </tr>
        <br/>
    </table>
    <input type="submit" value="<fmt:message key="label.buttonSignUp" bundle="${var}"/>">
</form>
<input type="button" value="<fmt:message key="label.back" bundle="${var}"/>" onClick='location.href="/jsp/login.jsp"'>
<hr/>
<c:import url="../jsp/common/footer.jsp" />
</body></html>