package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class AddDishCommand implements ActionCommand {
    private static final String PARAM_CHOOSEN_DISH = "choosenDish";
    private static final String PARAM_ORDERS = "orders";
    private MenuService receiver;

    public AddDishCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        ArrayList<Dish> orders = (ArrayList<Dish>) request.getSession().getAttribute(PARAM_ORDERS);
        String choosenDish = request.getParameter(PARAM_CHOOSEN_DISH);

        try {
            orders.add(receiver.findDishByName(choosenDish));
            request.getSession().setAttribute(PARAM_ORDERS,orders);
        } catch (ServiceException e) {
            ////////    как ошибку отправлять Forfard или REDIRECT
            page = ConfigurationManager.getProperty("path.page.login");
            router.setPagePath(page);
            return router;
        }
        page = ConfigurationManager.getProperty("path.page.main");
        router.setPagePath(page);
        return router;
    }
}
