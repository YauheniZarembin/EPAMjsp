package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class ChangeReceivedOrderCommand implements ActionCommand {

    private static final String PARAM_ORDER_ID = "chosenOrder";
    private static final String PARAM_ORDERS = "userOrders";

    private AdminService receiver;

    public ChangeReceivedOrderCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        String orderId = request.getParameter(PARAM_ORDER_ID);
        System.out.println(orderId+"execute");
        try {
            receiver.changeReceivedOrder(orderId);
            request.getSession().setAttribute(PARAM_ORDERS, receiver.findAllOrders());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        page = ConfigurationManager.getProperty("path.page.adminOrders");
        router.setPagePath(page);
        return router;
    }
}