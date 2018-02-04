package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderingCommand  implements ActionCommand{
    private static final String PARAM_IS_CASH_PAYMENT = "payment";
    private static final String PARAM_DATE_TIME_RECEIVING = "dateTimeOrder";
    private static final String PARAM_MESSAGE = "Message";
    private static final String PARAM_ORDER_COST = "orderCost";
    private static final String PARAM_ORDER_ID = "orderID";
    private static final String PARAM_COST_RESULT = "costResult";
    private static final String PARAM_USER = "user";
    private static final String PARAM_MAP_ORDER = "orders";

    private UserService receiver;

    public OrderingCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        Router router = new Router();
        String page = null;

        Map<Dish,Integer> orderMap = (HashMap<Dish,Integer>) request.getSession().getAttribute(PARAM_MAP_ORDER);
        BigDecimal orderCost = (BigDecimal) request.getSession().getAttribute(PARAM_ORDER_COST);
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        String dateString= request.getParameter(PARAM_DATE_TIME_RECEIVING);
        String payment = request.getParameter(PARAM_IS_CASH_PAYMENT);
        MessageManager messageManager = MessageManager.defineLocale(request);
        LocalDateTime date = LocalDateTime.parse(dateString);

        if (receiver.checkDateTimeOrder(date)){
            if ("1".equals(payment) ||  (user.getMoney().compareTo(orderCost) >= 0)) {
                try {
                    Order order = new Order(0, user.getUserName(), date, "1".equals(payment), orderCost, orderMap);
                    receiver.makeOrder(order, user);
                    request.getSession().setAttribute(PARAM_USER,user);
                    request.getSession().setAttribute(PARAM_ORDER_COST,null);
                    request.getSession().setAttribute(PARAM_MAP_ORDER,null);
                    request.getSession().setAttribute(PARAM_IS_CASH_PAYMENT,payment);
                    request.getSession().setAttribute(PARAM_COST_RESULT,orderCost);
                    request.getSession().setAttribute(PARAM_ORDER_ID,order.getOrderId());
                    request.getSession().setAttribute(PARAM_DATE_TIME_RECEIVING,date);
                    page = ConfigurationManager.getProperty("path.page.successOrdering");
                    router.setRoute(Router.RouteType.REDIRECT);
                } catch (ServiceException e) {
                    throw new CommandException(e.getMessage(), e.getCause());
                }
            }
            else{
                request.setAttribute(PARAM_MESSAGE,
                        messageManager.getMessage("message.notMoneyForPayment"));
                page = ConfigurationManager.getProperty("path.page.ordering");
            }
        }
        else{
            request.setAttribute(PARAM_MESSAGE,
                    messageManager.getMessage("message.dateNotCondition"));
            page = ConfigurationManager.getProperty("path.page.ordering");
        }


        router.setPagePath(page);
        return router;
    }
}
