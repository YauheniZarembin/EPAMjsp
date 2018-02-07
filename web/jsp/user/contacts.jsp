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
    <link rel="icon" href="/resource/image/epamcafe.jpg" type="images/jpg">
</head>
<body>
<header class="whiteback">
    <c:import url="/jsp/common/header.jsp" />
</header>
<div class="centerTable">
    <h1><fmt:message key="label.headerContacts" bundle="${var}"/></h1>
    <img src="/resource/image/epamcafe.jpg" width="270" height="330"
         alt="Иллюстрация" align="left"
         vspace="10" hspace="10">
    <p>
        <h3><fmt:message key="label.contactAddress" bundle="${var}"/></h3>
        <fmt:message key="label.contactAddressInfo" bundle="${var}"/>
    </p>
    <p>
        <h3><fmt:message key="label.contactTelephone" bundle="${var}"/></h3>
        <fmt:message key="label.telephone" bundle="${var}"/>
    </p>
    <p>
        <h3><fmt:message key="label.contactTime" bundle="${var}"/></h3>
        <fmt:message key="label.contactMnFr" bundle="${var}"/><br>
        <fmt:message key="label.contactSt" bundle="${var}"/><br>
        <fmt:message key="label.contactSn" bundle="${var}"/>
    </p>
    <p>
        </h3><fmt:message key="label.email" bundle="${var}"/>:</h3><br>
        <fmt:message key="label.emailinfo" bundle="${var}"/>
    </p>
</div>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
