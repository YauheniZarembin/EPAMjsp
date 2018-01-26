package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserOrdersCommand implements ActionCommand {

    private UserService receiver;
    private static final String PARAM_USER_NAME = "userName";
    private static final String PARAM_USER_ORDERS = "userOrders";
    private static final String PARAM_DISHES_MAP = "dishesMap";

    public UserOrdersCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try {
            String userName = request.getParameter(PARAM_USER_NAME);
            List<Order> userOrders = receiver.findOrdersByName(userName);
           //ArrayList<Map<String,Integer>> dishesInOrsers= new ArrayList<>();
            request.getSession().setAttribute(PARAM_USER_ORDERS, userOrders);
            //for(Order userOrder : userOrders){
            //    dishesInOrsers.add(receiver.findDishesByOrderId(userOrder.getOrderId()));
            //}
            //dishesInOrsers.get(0).k
            //request.getSession().setAttribute(PARAM_DISHES_MAP,dishesInOrsers);
        } catch (ServiceException e) {
            ////////    как ошибку отправлять Forfard или REDIRECT  ???????????
            page = ConfigurationManager.getProperty("path.page.login");
            router.setPagePath(page);
            return router;
        }

        page = ConfigurationManager.getProperty("path.page.userOrders");
        router.setPagePath(page);
        return router;
    }
}
