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
<div class="whiteback">
    <table width="100%"  border="1"  style="table-layout: fixed">
        <tr>
            <td colspan="3" align="center"><b><h1><fmt:message key="label.orders" bundle="${var}"/></h1></b></td>
        </tr>
        <tr  align="center">
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_order"/>
                <input type="hidden" name="ordersType" value="past"/>
                <td><input type="submit" value="<fmt:message key="label.notReceivedOrders" bundle="${var}"/>" style="height:40px;width:300px;"/></td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_order"/>
                <input type="hidden"  name="ordersType" value="today"/>
                <td><input type="submit" value="<fmt:message key="label.todayOrders" bundle="${var}"/>" style="height:40px;width:300px;"/></td>
            </form>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="admin_order"/>
                <input type="hidden"  name="ordersType" value="future"/>
                <td><input type="submit" value="<fmt:message key="label.futureOrders" bundle="${var}"/>" style="height:40px;width:300px;"/></td>
            </form>
        </tr>
    </table>
</div>
<br/>
<div class="whiteback">
    <c:if test="${empty userOrders}">
        <c:if test="${time eq 'TODAY'}">
            <td align="center"><h1><fmt:message key="label.emptyTodayOrders" bundle="${var}"/></h1></td>
        </c:if>
        <c:if test="${time eq 'PAST'}">
            <td align="center"><h1><fmt:message key="label.emptyPastOrders" bundle="${var}"/></h1></td>
        </c:if>
        <c:if test="${time eq 'FUTURE'}">
            <td align="center"><h1><fmt:message key="label.emptyFutureOrders" bundle="${var}"/></h1></td>
        </c:if>
    </c:if>
    <c:if test="${not empty userOrders}">
    <table width="100%" style="table-layout: fixed" border="1px">
        <c:if test="${time eq 'TODAY'}">
            <tr><td colspan="7" align="center"><h1><fmt:message key="label.todayOrders" bundle="${var}"/></h1></td></tr>
        </c:if>
        <c:if test="${time eq 'PAST'}">
            <tr><td colspan="6" align="center"><h1><fmt:message key="label.notReceivedOrders" bundle="${var}"/></h1></td></tr>
            <form name="localeForm" method="POST" action="/controller">
                <input type="hidden" name="command" value="delete_past_orders"/>
                <input type="hidden" name="pastOrders" value="${userOrders}"/>
                <td colspan="6" align="center"><input type="submit" value="<fmt:message key="label.buttonPastOrders" bundle="${var}"/>" style="height:40px;width:350px;"/></td>
            </form>
        </c:if>
        <c:if test="${time eq 'FUTURE'}">
            <tr><td colspan="6" align="center"><h1><fmt:message key="label.futureOrders" bundle="${var}"/></h1></td></tr>
        </c:if>
        <tr align="center">
            <td><h3><fmt:message key="label.orderId" bundle="${var}"/></h3></td>
            <td><h3><fmt:message key="label.orderUser" bundle="${var}"/></h3></td>
            <td><h3><fmt:message key="label.orderDate" bundle="${var}"/></h3></td>
            <td><h3><fmt:message key="label.orderDishList" bundle="${var}"/></h3></td>
            <td><h3><fmt:message key="label.orderCost" bundle="${var}"/></h3></td>
            <td><h3><fmt:message key="label.orderPayment" bundle="${var}"/></h3></td>
            <c:if test="${time eq 'TODAY'}">
            <td><h3><fmt:message key="label.orderReceived" bundle="${var}"/> ? </h3></td>
            </c:if>
        </tr>
        <c:forEach items="${userOrders}" var="userOrder">
            <tr align="center">
                <td>${userOrder.orderId}</td>
                <td>${userOrder.userName}</td>
                <td>${userOrder.dateOfReceiving}</td>
                <td>
                    <table>
                        <c:forEach items="${userOrder.dishes}" var="orderDish">
                            <tr>
                                <td>${orderDish.key.dishName}</td>
                                <td><fmt:message key="label.amount" bundle="${var}"/>${orderDish.value}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
                <td>${userOrder.orderCost}</td>
                <td>
                    <c:if test="${userOrder.isCashPayment()}">
                        <fmt:message key="label.orderCash" bundle="${var}"/>
                    </c:if>
                    <c:if test="${!userOrder.isCashPayment()}">
                        <fmt:message key="label.orderSite" bundle="${var}"/>
                    </c:if>
                </td>
                <c:if test="${time eq 'TODAY'}">
                    <form name="localeForm" method="POST" action="/controller">
                        <input type="hidden" name="command" value="delete_order"/>
                        <input type="hidden" name="chosenOrder" value="${userOrder.orderId}"/>
                        <td><input type="submit" value="<fmt:message key="label.orderReceived" bundle="${var}"/>" ></td>
                    </form>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    </c:if>
</div>
<c:import url="/jsp/user/common/footer.jsp" />
</body>
</html>
