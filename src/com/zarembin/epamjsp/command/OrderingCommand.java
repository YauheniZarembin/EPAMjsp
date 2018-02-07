package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.Dish;
import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.resource.MessageManager;
import com.zarembin.epamjsp.service.UserService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class OrderingCommand  implements ActionCommand{
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String IS_CASH_PAYMENT = "1";
    private static final String PARAM_IS_CASH_PAYMENT = "payment";
    private static final String PARAM_DATE_TIME_RECEIVING = "dateTimeOrder";
    private static final String PARAM_MESSAGE = "Message";
    private static final String PARAM_ORDER_COST = "orderCost";
    private static final String PARAM_ORDER_ID = "orderID";
    private static final String PARAM_COST_RESULT = "costResult";
    private static final String PARAM_USER = "user";
    private static final String PARAM_MAP_ORDER = "orders";
    private static final String PARAM_LOCALE = "changeLanguage";

    private UserService receiver;

    public OrderingCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {


        Router router = new Router();
        String page;
        MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute(PARAM_LOCALE));

        Map<Dish,Integer> orderMap = (HashMap<Dish,Integer>) request.getSession().getAttribute(PARAM_MAP_ORDER);
        BigDecimal orderCost = (BigDecimal) request.getSession().getAttribute(PARAM_ORDER_COST);
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        String payment = request.getParameter(PARAM_IS_CASH_PAYMENT);
        String dateString= request.getParameter(PARAM_DATE_TIME_RECEIVING);
        LocalDateTime date = LocalDateTime.parse(dateString);

        if (receiver.checkDateTimeOrder(date)){
            if (IS_CASH_PAYMENT.equals(payment) ||  (user.getMoney().compareTo(orderCost) >= 0)) {
                try {
                    Order order = new Order(0, user.getUserName(), date, IS_CASH_PAYMENT.equals(payment), orderCost, orderMap);
                    receiver.makeOrder(order, user);
                    request.getSession().setAttribute(PARAM_USER,user);
                    request.getSession().setAttribute(PARAM_ORDER_COST,null);
                    request.getSession().setAttribute(PARAM_MAP_ORDER,null);
                    request.getSession().setAttribute(PARAM_IS_CASH_PAYMENT,payment);
                    request.getSession().setAttribute(PARAM_COST_RESULT,orderCost);
                    request.getSession().setAttribute(PARAM_ORDER_ID,order.getOrderId());
                    request.getSession().setAttribute(PARAM_DATE_TIME_RECEIVING,date);
                    page = ConfigurationManager.getProperty("path.page.successOrdering");
                    LOGGER.log(Level.INFO,"Making order number "+String.valueOf(order.getOrderId()));
                    router.setRoute(Router.RouteType.REDIRECT);
                } catch (ServiceException e) {
                    throw new CommandException(e.getMessage(), e);
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
