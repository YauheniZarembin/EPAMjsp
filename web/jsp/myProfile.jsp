<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 25.01.2018
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
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
    <c:import url="../jsp/common/header.jsp" />
</header>
<div class="whiteback" style="width: 60%">
    <h1><fmt:message key="label.myProfile" bundle="${var}"/></h1>
    <p>
        <b><fmt:message key="label.profileName" bundle="${var}"/>:</b>     ${user.name}
    </p>
    <br/>
    <p>
        <b><fmt:message key="label.profileLastname" bundle="${var}"/>:</b>    ${user.lastname}
    </p>
    <br/>
    <p>
        <b>E-mail:</b>   ${user.eMail}
    </p>
    <br/>
    <p>
        <b><fmt:message key="label.profileNumberOfOrders" bundle="${var}"/>:</b>    ${user.numberOfOrders}
    </p>
    <br/>
    <p>
        <b><fmt:message key="label.profileLoyaltyPoints" bundle="${var}"/>:</b>     ${user.loyaltyPoints}
    </p>
    <br/>
    <p>
        <b><fmt:message key="label.profileMoney" bundle="${var}"/>:</b>      ${user.money}
    </p>
    <p>
        <b><fmt:message key="label.cardNumber" bundle="${var}"/>:</b>      ${user.cardNumber}
    </p>

    <table>
    <form name="loginForm" method="POST" action="/controller">
        <input type="hidden" name="command" value="top_up"/>
        <tr><td> <fmt:message key="label.cardPassword" bundle="${var}"/><span class="req">*</span></td>
        <td><input type="password" name="cardPassword" value=""/></td></tr>
        <tr><td> <fmt:message key="label.cardMoney" bundle="${var}"/><span class="req">*</span></td>
            <td><input type="text" name="newMoney" value=""/></td></tr>
        <tr> <td colspan="2"> ${Message} </td></tr>
        <tr> <td> <input type="submit" value="<fmt:message key="label.topUp" bundle="${var}"/>"/> </td></tr>
    </form>

    </table>
</div>


<c:import url="../jsp/common/footer.jsp" />
</body>
</html>
