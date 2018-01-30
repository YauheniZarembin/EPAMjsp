<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 24.01.2018
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<header>
    <c:import url="/jsp/admin/adminHeader.jsp" />
</header>
<div class="whiteback" style="width: 40%">
    <h1><fmt:message key="label.headerReviews" bundle="${var}"/></h1>
    <table width="80%" float="left">
        <c:forEach items="${reviews}" var="review">
        <c:choose>
        <c:when test="${ review.mark > 7 }" >
        <tr><td><b><fmt:message key="label.userRoleUser" bundle="${var}"/>:</b>      ${review.userName}
            <b><fmt:message key="label.mark" bundle="${var}"/></b>:           ${review.mark}</td>
        </tr>
        <tr><td style="background-color: rgba(0,255,0,0.5)">${review.textReview}</td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="delete_review"/>
                    <input type="hidden"  name="reviewId" value=${review.reviewId} />
                    <td><input type="image" src="/resource/image/cross.jpg" height="20" width="20"></td>
                </form>
        </tr>
        </c:when>
        <c:when test="${ review.mark < 5 }" >
        <tr><td><b><fmt:message key="label.userRoleUser" bundle="${var}"/>:</b>      ${review.userName}
            <b><fmt:message key="label.mark" bundle="${var}"/></b>:           ${review.mark}</td></tr>
        <tr><td style="background-color: rgba(255,0,0,0.5)">${review.textReview}</td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="delete_review"/>
                    <input type="hidden"  name="reviewId" value=${review.reviewId} />
                    <td><input type="image" src="/resource/image/cross.jpg" height="20" width="20"></td>
                </form>
            </tr>

</div>
</c:when>
<c:otherwise>
    <tr><td><b><fmt:message key="label.userRoleUser" bundle="${var}"/>:</b>      ${review.userName}
        <b><fmt:message key="label.mark" bundle="${var}"/></b>:           ${review.mark}</td></tr>
    <tr><td>${review.textReview}</td>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="delete_review"/>
                <input type="hidden"  name="reviewId" value=${review.reviewId} />
                <td><input type="image" src="/resource/image/cross.jpg" height="20" width="20"></td>
            </form>
        </tr>
</c:otherwise>
</c:choose>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
</c:forEach>
</table>
</div>

<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
