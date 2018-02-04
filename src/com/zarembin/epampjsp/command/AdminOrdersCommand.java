package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class AdminOrdersCommand implements ActionCommand {

    public enum TimeOfOrders {
        PAST , TODAY , FUTURE
    }

    private AdminService receiver;
    private static final String PARAM_ORDERS = "userOrders";
    private static final String PARAM_ORDERS_TIME_TYPE = "ordersType";
    private static final String PARAM_TIME = "time";

    public AdminOrdersCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        List<Order> orders;
        TimeOfOrders timeOfOrders = TimeOfOrders.valueOf(request.getParameter(PARAM_ORDERS_TIME_TYPE).toUpperCase());
        try {
            switch (timeOfOrders){
                case PAST:
                    orders = receiver.findPastOrders();
                    request.getSession().setAttribute(PARAM_TIME,timeOfOrders.toString());
                    request.getSession().setAttribute(PARAM_ORDERS,orders);
                    break;
                case TODAY:
                    orders = receiver.findTodayOrders();
                    request.getSession().setAttribute(PARAM_TIME,timeOfOrders.toString());
                    request.getSession().setAttribute(PARAM_ORDERS,orders);
                    break;
                case FUTURE:
                    orders = receiver.findFutureOrders();
                    request.getSession().setAttribute(PARAM_TIME,timeOfOrders.toString());
                    request.getSession().setAttribute(PARAM_ORDERS,orders);
                    break;
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e.getCause());
        }

        page = ConfigurationManager.getProperty("path.page.adminOrders");
        router.setPagePath(page);
        return router;
    }
}
