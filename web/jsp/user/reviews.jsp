<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 25.01.2018
  Time: 0:26
  To change this template use File | Settings | File Templates.
--%>
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
<header>
    <c:import url="/jsp/user/common/header.jsp" />
</header>
<div class="centerTable">
    <h1><fmt:message key="label.headerReviews" bundle="${var}"/></h1>
    <table width="80%" float="left">
    <c:forEach items="${reviews}" var="review">
        <c:choose>
            <c:when test="${ review.mark > 7 }" >
                    <tr><td><b><fmt:message key="label.userRoleUser" bundle="${var}"/>:</b>      ${review.userName}
                        <b><fmt:message key="label.mark" bundle="${var}"/></b>:           ${review.mark}</td>
                   </tr>
                    <tr><td style="background-color: rgba(0,255,0,0.5)">${review.textReview}</td>
                    <c:if test="${user.userName eq review.userName}">
                        <form name="localeForm" method="POST" action="/controller">
                            <input type="hidden" name="command" value="delete_review"/>
                            <input type="hidden"  name="reviewId" value=${review.reviewId} />
                            <td><input type="image" src="/resource/image/cross.jpg" height="20" width="20"></td>
                        </form>
                    </c:if>
                    </tr>
            </c:when>
            <c:when test="${ review.mark < 5 }" >
                    <tr><td><b><fmt:message key="label.userRoleUser" bundle="${var}"/>:</b>      ${review.userName}
                        <b><fmt:message key="label.mark" bundle="${var}"/></b>:           ${review.mark}</td></tr>
                     <tr><td style="background-color: rgba(255,0,0,0.5)">${review.textReview}</td>
                         <c:if test="${user.userName eq review.userName}">
                             <form name="localeForm" method="POST" action="/controller">
                                 <input type="hidden" name="command" value="delete_review"/>
                                 <input type="hidden"  name="reviewId" value=${review.reviewId} />
                                 <td><input type="image" src="/resource/image/cross.jpg" height="20" width="20"></td>
                             </form>
                         </c:if></tr>

                </div>
            </c:when>
            <c:otherwise>
                <tr><td><b><fmt:message key="label.userRoleUser" bundle="${var}"/>:</b>      ${review.userName}
                    <b><fmt:message key="label.mark" bundle="${var}"/></b>:           ${review.mark}</td></tr>
                <tr><td>${review.textReview}</td>
                    <c:if test="${user.userName eq review.userName}">
                        <form name="localeForm" method="POST" action="/controller">
                            <input type="hidden" name="command" value="delete_review"/>
                            <input type="hidden"  name="reviewId" value=${review.reviewId} />
                            <td><input type="image" src="/resource/image/cross.jpg" height="20" width="20"></td>
                        </form>
                    </c:if></tr>
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
    <c:if test="${not empty user}">
        <h3><fmt:message key="label.giveFeedback" bundle="${var}"/></h3>
        <form name="localeForm" id="addFormId" method="POST" action="/controller">
            <input type="hidden" name="command" value="add_review"/>
            <b><fmt:message key="label.mark" bundle="${var}"/></b>:
            <select name="mark">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
            </select>
            <br>
            <b><fmt:message key="label.textReview" bundle="${var}"/></b>
            <br>
            <textarea rows="4" cols="50" name="comment" form="addFormId" ></textarea>
            <br>
            <b>${MessageReview}</b>
            <br>
            <input type="submit" style="width: 200px; height: 50px;" value="<fmt:message key="label.sendReview" bundle="${var}"/>" />
            <br>
            <br>
            <br>
        </form>
    </c:if>

</div>
<c:import url="/jsp/user/common/footer.jsp" />
</body>
</html>
