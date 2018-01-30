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

    private AdminService receiver;
    private static final String PARAM_ORDERS = "userOrders";

    public AdminOrdersCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        try {
            List<Order> orders = receiver.findAllOrders();
            for(Order order:orders){
                System.out.println(order);
            }
            request.getSession().setAttribute(PARAM_ORDERS,orders);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e.getCause());
        }

        page = ConfigurationManager.getProperty("path.page.adminOrders");
        router.setPagePath(page);
        return router;
    }
}
