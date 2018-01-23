<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
</head>
<div class="wrapper">
<body>
<header>
    <c:import url="../jsp/common/header.jsp" />
</header>
<div class="whiteback" >
<form name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="login"/>
    <fmt:message key="label.login" bundle="${var}"/><span class="req">*</span><br/>
    <input type="text" name="login" value=""/>
    <br/><fmt:message key="label.password" bundle="${var}"/><span class="req">*</span><br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${Message}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <input type="submit" value="<fmt:message key="label.buttonlogin" bundle="${var}"/>"/>
    <input type="button" value="<fmt:message key="label.buttonsignup" bundle="${var}"/>"  onClick='location.href="/jsp/signup.jsp"' />
</form>
</div>
<hr/>
<c:import url="../jsp/common/footer.jsp"/>
</body>
    </div>
</html>