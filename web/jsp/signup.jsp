<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html><head>
    <title>EPAM-cafe</title>
    <style>
        @import "/css/styleSighUp.css";
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
            <td><input type="text" name="username" value="" required pattern="\w+"/></td>
            <td ><fmt:message key="label.passwordCondition" bundle="${var}"/></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.password" bundle="${var}"/></td>
            <td><input type="password" name="password" value="" required pattern="\w+"/></td>
            <td ><fmt:message key="label.passwordCondition" bundle="${var}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="label.singUpName" bundle="${var}"/></td>
            <td><input type="text" name="name" value=""/></td>
        </tr>
        <tr>
            <td><fmt:message key="label.signUpLastname" bundle="${var}"/></td>
            <td><input type="text" name="lastname" value=""/></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.signUpEmail" bundle="${var}"/></td>
            <td><input type="text" name="email" value="" required pattern="^([\w-]+\.)*[\w-]+@[\w-]+(\.[\w-]+)*\.[a-z]{2,6}$" /></td>
        </tr>
        <tr>
            <td colspan="3" align="center" ><hr/></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.signUpCardNumber" bundle="${var}"/></td>
            <td><input type="text" name="card number" value=""  required pattern="\d{4}"/></td>
            <td ><fmt:message key="label.cardNumberCondition" bundle="${var}"/></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.signUpCardPassword" bundle="${var}"/></td>
            <td><input type="password" name="card password" value=""  required pattern="\d{4}" /></td>
        </tr>
        <tr>
            <td colspan="3" align="center" ><b>${errorMessage}</b></td>
        </tr>
        <tr>
            <td colspan="3" align="center"><input type="submit" value="<fmt:message key="label.buttonSignUp" bundle="${var}"/>" style="height:40px;width: 400px;"></td>
        </tr>
        <br/>
    </table>
</form>
<c:import url="../jsp/common/footer.jsp" />
</body></html>