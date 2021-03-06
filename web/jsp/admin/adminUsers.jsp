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
<table width="100%" float="left" class="whiteback">
    <tr>
        <td><h1><fmt:message key="label.userLogin" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userName" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userLastname" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userOrders" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userPoints" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userBan" bundle="${var}"/></h1></td>
        <td><h1><fmt:message key="label.userRole" bundle="${var}"/></h1></td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.userName}</td>
            <td>${user.name}</td>
            <td>${user.lastname}</td>
            <td>${user.numberOfOrders}</td>
            <td>${user.loyaltyPoints}</td>
            <td>
                <c:if test="${user.isBan()}">
                    <table width="100%" style="table-layout: fixed">
                        <tr>
                            <td>
                                <fmt:message key="label.userBanYes" bundle="${var}"/>
                            </td>
                            <c:if test="${!user.isAdmin()}">
                                <form name="localeForm" method="POST" action="/controller">
                                    <input type="hidden" name="command" value="change_Ban"/>
                                    <input type="hidden" name="editUserName" value="${user.userName}"/>
                                    <input type="hidden" name="editUserBan" value="${user.isBan()}"/>
                                    <td><input type="submit" value="<fmt:message key="label.deleteBan" bundle="${var}"/>" ></td>
                                </form>
                            </c:if>
                        </tr>
                    </table>
                </c:if>
                <c:if test="${!user.isBan()}">
                    <table width="100%" style="table-layout: fixed">
                        <tr>
                            <td>
                                <fmt:message key="label.userBanNo" bundle="${var}"/>
                            </td>
                            <c:if test="${!user.isAdmin()}">
                            <form name="localeForm" method="POST" action="/controller">
                                <input type="hidden" name="command" value="change_Ban"/>
                                <input type="hidden" name="editUserName" value="${user.userName}"/>
                                <input type="hidden" name="editUserBan" value="${user.isBan()}"/>
                                <td><input type="submit" value="<fmt:message key="label.setBan" bundle="${var}"/>" ></td>
                            </form>
                            </c:if>
                        </tr>
                    </table>
                </c:if>
            </td>
            <td>
                <c:if test="${user.isAdmin()}">
                    <fmt:message key="label.userRoleAdmin" bundle="${var}"/>
                </c:if>
                <c:if test="${!user.isAdmin()}">
                    <fmt:message key="label.userRoleUser" bundle="${var}"/>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>