<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html><head>
    <style>
        @import "/css/style.css";
    </style>
    <title>Error Page</title>
    <link rel="icon" href="/resource/image/epamcafe.jpg" type="images/jpg">
</head>
<body>
<c:if test="${!user.isAdmin()}">
    <c:import url="/jsp/common/header.jsp" />
</c:if>
<c:if test="${user.isAdmin()}">
    <c:import url="../admin/adminHeader.jsp" />
</c:if>
<div class="whiteback" style="font-size: 21px">
    <b>Request from ${pageContext.errorData.requestURI} is failed</b>
    <br/>
    <br/>
    <b>Servlet name or type:</b> ${pageContext.errorData.servletName}
    <br/>
    <br/>
    <b>Status code:</b> ${pageContext.errorData.statusCode}
    <br/>
    <br/>
    <b>Exception:</b> ${pageContext.errorData.throwable}
    <br/>
    <br/>
    <b>${wrongAction} 1</b>
    <br/>
    <br/>
    <b>${exceptionCause} 3</b>
    <br/>
    <br/>
    <b>${exceptionMessage} 4</b>
    <c:import url="/jsp/common/footer.jsp" />
</div>
</body>
</html>