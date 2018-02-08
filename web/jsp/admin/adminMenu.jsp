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
<c:import url="/jsp/common/menu.jsp"/>
<input type="button" value="<fmt:message key="label.newDish" bundle="${var}"/>"  onClick='location.href="/jsp/admin/adminAddDish.jsp"' class="buttonAddDish" />
<table width="100%" float="left" class="whiteback">
    <tr>
        <td><b><fmt:message key="label.dishName" bundle="${var}"/></b></td>
        <td><b><fmt:message key="label.dishPrice" bundle="${var}"/></b></td>
        <td><b><fmt:message key="label.changePriceMax" bundle="${var}"/></b></td>
        <td></td>
        <td><b><fmt:message key="label.dishPicture" bundle="${var}"/></b></td>
        <td><b><fmt:message key="label.changePicture" bundle="${var}"/></b></td>
        <td></td>
        <td><b><fmt:message key="label.delete" bundle="${var}"/></b></td>
    </tr>
    <c:forEach items="${dishes}" var="dish">
        <c:if test="${!dish.isNoMore()}">
        <tr>
            <td>${dish.dishName}</td>
            <td>${dish.price}</td>
            <td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="edit_dish_price"/>
                    <input type="hidden" name="chosenDish" value="${dish.dishName}"/>
                    <input type="hidden" name="typeChosenDish" value="${dish.typeOfDish}"/>
                    <input type="text" name="newPrice" required pattern="\d{1,6}(\.\d+)?" /><br/>
                        ${Message}<br/>
            <input type="submit" value="<fmt:message key="label.changePrice" bundle="${var}"/>" >
            </form>
            </td>
            <td></td>
            <td><img src="${dish.imagePath}" height="100" width="100" border="2px"></td>
            <td>
                <form action="/upload" enctype="multipart/form-data" method="POST">

                    <input id="${dish.dishName}" style="visibility: hidden" onchange="this.form.submit()" name="userfile" accept="image/*" type="file" required>
                    <div id="btn">
                        <label for="${dish.dishName}"><fmt:message key="label.choosePicture" bundle="${var}"/></label>
                    </div>
                    <input type="hidden" name="chosenDish" value="${dish.dishName}"/>
                    <input type="hidden" name="typeChosenDish" value="${dish.typeOfDish}"/>
                </form>
            </td>
            <td></td>
                <form name="localeForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="dish_no_more"/>
                    <input type="hidden" name="chosenDish" value="${dish.dishName}"/>
                    <input type="hidden" name="typeChosenDish" value="${dish.typeOfDish}"/>
                <td><input type="image" src="/resource/image/cross.jpg" height="50" width="50"></td>
            </form>
        </tr>
        </c:if>
    </c:forEach>
</table>
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>
