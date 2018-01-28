package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddDishCommand implements ActionCommand {
    private static final String PARAM_CHOOSEN_DISH = "choosenDish";
    private static final String PARAM_ORDERS = "orders";
    private static final String PARAM_ORDER_COST = "orderCost";
    private MenuService receiver;

    public AddDishCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        BigDecimal orderCost= (BigDecimal) request.getSession().getAttribute(PARAM_ORDER_COST);
        Map<Dish,Integer> orders= (HashMap<Dish,Integer>) request.getSession().getAttribute(PARAM_ORDERS);
        String choosenDish = request.getParameter(PARAM_CHOOSEN_DISH);

        try {
            ////////////////    скорее всего это надо в receiver
            if (orderCost == null){
                orderCost = new BigDecimal(0);
            }
            if (orders == null){
                orders = new HashMap<>();
            }

            Dish dish = receiver.findDishByName(choosenDish);
            System.out.println(dish.getDishName());

            if (orders.containsKey(dish)){
                orders.replace(dish,orders.get(dish)+1);
            }
            else{
                orders.put(dish,1);
            }
            orderCost = orderCost.add(dish.getPrice());

            request.getSession().setAttribute(PARAM_ORDERS,orders);
            request.getSession().setAttribute(PARAM_ORDER_COST,orderCost);


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
