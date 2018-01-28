package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserOrdersCommand implements ActionCommand {

    private UserService receiver;
    private static final String PARAM_USER = "user";
    private static final String PARAM_USER_ORDERS = "userOrders";

    public UserOrdersCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try {
            User user = (User) request.getSession().getAttribute(PARAM_USER);
            List<Order> userOrders;
            userOrders = receiver.findOrdersByName(user.getUserName());
            request.getSession().setAttribute(PARAM_USER_ORDERS,userOrders);
            page = ConfigurationManager.getProperty("path.page.userOrders");
        } catch (ServiceException e) {
            ////////    как ошибку отправлять Forfard или REDIRECT  ???????????
            page = ConfigurationManager.getProperty("path.page.login");
            router.setPagePath(page);
            return router;
        }

        router.setPagePath(page);
        return router;
    }
}
