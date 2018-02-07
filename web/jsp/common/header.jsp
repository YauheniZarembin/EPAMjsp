<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>
    <table width="100%" class="whiteheader">
        <tr  align="center">
            <c:if test="${not empty user}">
            <td>
                <table class="whiteback" height="5" border="5">
                    <tr>
                        <td colspan="3" align="center"><ctg:role user="${user}"/></td>
                    </tr>
                    <tr>
                        <td><a href='/jsp/loginuser/profile.jsp' style="color: black"><fmt:message key="label.myProfile" bundle="${var}"/></a></td>
                        <form name="localeFormOrders" method="POST" action="/controller">
                            <input type="hidden" name="command" value="user_orders"/>
                            <td><a href='#' style="color:black" onClick="document.forms['localeFormOrders'].submit();"><fmt:message key="label.myOrders" bundle="${var}"/></a></td>
                        </form>
                        <form name="localeFormOut" method="POST" action="/controller">
                            <input type="hidden" name="command" value="log_out"/>
                            <td><a href='#' style="color:black" onClick="document.forms['localeFormOut'].submit();"><fmt:message key="label.logOut" bundle="${var}"/></a></td>
                        </form>
                    </tr>
                </table>
            </td>
            </c:if>
            <c:if test="${empty user}">
                <td align="left"><b><h2><fmt:message key="label.epamCafeWelcome" bundle="${var}"/></h2></b></td>
            </c:if>
            <c:if test="${empty user}">
                <td><a href='/jsp/user/login.jsp' style="color: white"><fmt:message key="label.enterSite" bundle="${var}"/></a></td>
            </c:if>
            <form name="localeFormMenu" method="POST" action="/controller">
                <input type="hidden" name="command" value="type_Of_Dish"/>
                <input type="hidden" name="dishType" value="soup"/>
                <td><a href='#' style="color:white" onClick="document.forms['localeFormMenu'].submit();"><fmt:message key="label.headerMenu" bundle="${var}"/></a></td>
            </form>
            <td><a href='/jsp/user/contacts.jsp' style="color: white"><fmt:message key="label.headerContacts" bundle="${var}"/></a></td>
            <td><a href='/jsp/user/aboutUs.jsp' style="color: white"><fmt:message key="label.headerAboutUs" bundle="${var}"/></a></td>
            <form name="localeForm9" method="POST" action="/controller">
                <input type="hidden" name="command" value="reviews_command"/>
                <td><a href='#' style="color: white" onClick="document.forms['localeForm9'].submit();"><fmt:message key="label.headerReviews" bundle="${var}"/></a> </td>
            </form>
            <td>
                <table class="whiteheader" align="center">
                <tr align="center">
                    <form name="localeForm" method="POST" action="/controller">
                        <input type="hidden" name="pagePath" value="${pageContext.request.requestURL}" />
                        <input type="hidden" name="command" value="locale"/>
                        <td><input type="image" src="<fmt:message key="label.pictureLocale" bundle="${var}"/>" height="20" width="30" alt="<fmt:message key="label.buttonLanguage" bundle="${var}"/>"> </td>
                    </form>
                </tr>
                <tr><td><ctg:infoTimeTag/></td></tr>
                </table>
            </td>
        </tr>
    </table>
</header>
</body>
</html>
