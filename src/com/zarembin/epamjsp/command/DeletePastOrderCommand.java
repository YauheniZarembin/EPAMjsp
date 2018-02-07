package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.AdminService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeletePastOrderCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_ORDERS = "userOrders";
    private AdminService receiver;

    public DeletePastOrderCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        List<Order> orders = (ArrayList<Order>) request.getSession().getAttribute(PARAM_ORDERS);
        try {
            for(Order order:orders){
                receiver.deleteLoyaltyPoints(order.getUserName());
                receiver.deleteOrder(String.valueOf(order.getOrderId()));
                LOGGER.log(Level.INFO,"Deleting past order number "+String.valueOf(order.getOrderId()));
            }
            request.getSession().setAttribute(PARAM_ORDERS,null);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e);
        }
        page = ConfigurationManager.getProperty("path.page.adminOrders");
        router.setPagePath(page);
        return router;
    }
}
