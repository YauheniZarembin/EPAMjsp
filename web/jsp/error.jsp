<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html><head>
    <style>
        @import "/css/style1.css";
    </style>
    <title>Error Page</title>
</head>
<body>
Request from ${pageContext.errorData.requestURI} is failed
<br/>
Servlet name or type: ${pageContext.errorData.servletName}
<br/>
Status code: ${pageContext.errorData.statusCode}
<br/>
Exception: ${pageContext.errorData.throwable}
<c:import url="../jsp/common/footer.jsp" />
</body></html>