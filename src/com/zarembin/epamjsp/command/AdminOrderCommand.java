package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.AdminService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class AdminOrderCommand implements ActionCommand {

    public enum TimeOfOrders {
        PAST , TODAY , FUTURE
    }

    private AdminService receiver;
    private static final String PARAM_ORDERS = "userOrders";
    private static final String PARAM_ORDERS_TIME_TYPE = "ordersType";
    private static final String PARAM_TIME = "time";

    public AdminOrderCommand(AdminService receiver) {
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
            throw new CommandException(e.getMessage(),e);
        }

        page = ConfigurationManager.getProperty("path.page.adminOrders");
        router.setPagePath(page);
        return router;
    }
}
