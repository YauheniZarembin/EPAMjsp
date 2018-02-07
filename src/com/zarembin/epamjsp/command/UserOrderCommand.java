package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.UserService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserOrderCommand implements ActionCommand {

    private UserService receiver;
    private static final String PARAM_USER = "user";
    private static final String PARAM_USER_ORDERS = "userOrders";

    public UserOrderCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        try {
            User user = (User) request.getSession().getAttribute(PARAM_USER);
            List<Order> userOrders;
            userOrders = receiver.findOrdersByName(user.getUserName());

            request.getSession().setAttribute(PARAM_USER_ORDERS,userOrders);
            page = ConfigurationManager.getProperty("path.page.userOrders");
            router.setPagePath(page);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
