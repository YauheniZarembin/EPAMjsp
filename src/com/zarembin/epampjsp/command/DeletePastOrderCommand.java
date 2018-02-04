package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DeletePastOrderCommand implements ActionCommand {

    private static final String PARAM_ORDERS = "userOrders";
    private AdminService receiver;

    public DeletePastOrderCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page = null;
        List<Order> orders = (ArrayList<Order>) request.getSession().getAttribute(PARAM_ORDERS);
        System.out.println(orders);

        try {
            for(Order order:orders){
                receiver.deleteLoyaltyPoints(order.getUserName());
                receiver.deleteOrder(String.valueOf(order.getOrderId()));
                request.getSession().setAttribute(PARAM_ORDERS,null);
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e);
        }
        page = ConfigurationManager.getProperty("path.page.adminOrders");
        router.setPagePath(page);
        return router;
    }
}
