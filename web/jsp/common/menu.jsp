<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 22.01.2018
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table width="100%"  border="1"  class="whiteback">
        <tr>
            <td colspan="5" align="center"><b><h1><fmt:message key="label.menu" bundle="${var}"/></h1></b></td>
        </tr>
        <tr  align="center">
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="typeOfDish"/>
                <input type="hidden" name="dishType" value="soup"/>
                <td><input type="submit" value="<fmt:message key="label.soup" bundle="${var}"/>"/></td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="typeOfDish"/>
                <input type="hidden"  name="dishType" value="basic"/>
                <td><input type="submit" value="<fmt:message key="label.basic" bundle="${var}"/>"/></td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="typeOfDish"/>
                <input type="hidden"  name="dishType" value="side_dish" />
                <td><input type="submit" value="<fmt:message key="label.sidedish" bundle="${var}"/>"/></td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="typeOfDish"/>
                <input type="hidden"   name="dishType" value="dessert" />
                <td><input type="submit" value="<fmt:message key="label.dessert" bundle="${var}"/>"/></td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="typeOfDish"/>
                <input type="hidden"  name="dishType" value="drink" />
                <td><input type="submit" value="<fmt:message key="label.drink" bundle="${var}"/>"/></td>
            </form>
        </tr>
    </table>
</body>
</html>
