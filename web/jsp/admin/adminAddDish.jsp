<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${changeLanguage}"/>
<fmt:setBundle basename="resource.pagecontent" var="var"/>
<html>
<head>
    <title>EPAM-cafe</title>
    <style>
        @import "/css/styleSighUp2.css";
    </style>
    <link rel="icon" href="/resource/image/epamcafe.jpg" type="images/jpg">
</head>
<body>
<header>
    <c:import url="/jsp/admin/adminHeader.jsp" />
</header>
<form  name="loginForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="admin_add_dish"/>
    <table align="center" class="whiteback">
        <tr>
            <td colspan="3" align="center"><b><h1><fmt:message key="label.adding" bundle="${var}"/></h1></b></td>
        </tr>
        <tr>
            <td><fmt:message key="label.addingName" bundle="${var}"/></td>
            <td><input type="text" name="dishName" /></td>
        </tr>
        <tr>
            <td ><fmt:message key="label.addingDishType" bundle="${var}"/></td>
            <td>
                <select name="dishType">
                    <option value="basic"><fmt:message key="label.basic" bundle="${var}"/></option>
                    <option value="soup"><fmt:message key="label.soup" bundle="${var}"/></option>
                    <option value="dessert"><fmt:message key="label.dessert" bundle="${var}"/></option>
                    <option value="drink"><fmt:message key="label.drink" bundle="${var}"/></option>
                    <option value="side_dish"><fmt:message key="label.sideDish" bundle="${var}"/></option>
                </select>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="label.addingDishPrice" bundle="${var}"/></td>
            <td><input type="text" name="dishPrice" value="" required pattern="\d+(\.\d+)?"/></td>
        </tr>
        <tr>
            <td colspan="3" align="center" ><b>${errorMessage}</b></td>
        </tr>
        <tr>
            <td colspan="3" align="center"><input type="submit" value="<fmt:message key="label.newDish" bundle="${var}"/>" style="height:40px;width: 400px;"></td>
        </tr>
        <br/>
    </table>
</form>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
