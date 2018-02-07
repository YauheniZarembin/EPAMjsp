package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.AdminService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteOrderCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_CHOSEN_ORDER_ID="chosenOrder";
    private static final String PARAM_ORDERS = "userOrders";

    private AdminService receiver;

    public DeleteOrderCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        String orderId  = request.getParameter(PARAM_CHOSEN_ORDER_ID);

        try {
            receiver.deleteOrder(orderId);
            List<Order> orders = receiver.findTodayOrders();
            request.getSession().setAttribute(PARAM_ORDERS,orders);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e);
        }
        page = ConfigurationManager.getProperty("path.page.adminOrders");
        router.setPagePath(page);
        LOGGER.log(Level.INFO,"Deleting order number "+orderId);
        return router;
    }
}
