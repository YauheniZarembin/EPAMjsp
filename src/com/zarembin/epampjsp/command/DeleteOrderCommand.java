package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SAAJResult;
import java.util.List;

public class DeleteOrderCommand implements ActionCommand {

    private static final String PARAM_CHOSEN_ORDER_ID="chosenOrder";
    private static final String PARAM_ORDERS = "userOrders";

    private AdminService receiver;

    public DeleteOrderCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page = null;
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
        return router;
    }
}
