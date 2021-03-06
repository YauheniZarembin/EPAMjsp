package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.Dish;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.MenuService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddDishCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_CHOSEN_DISH = "chosenDish";
    private static final String PARAM_ORDERS = "orders";
    private static final String PARAM_ORDER_COST = "orderCost";
    private MenuService receiver;

    public AddDishCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        BigDecimal orderCost= (BigDecimal) request.getSession().getAttribute(PARAM_ORDER_COST);
        Map<Dish,Integer> orders= (HashMap<Dish,Integer>) request.getSession().getAttribute(PARAM_ORDERS);
        String chosenDish = request.getParameter(PARAM_CHOSEN_DISH);

        try {
            Dish dish = receiver.findDishByName(chosenDish);
            request.getSession().setAttribute(PARAM_ORDERS,receiver.addDish(orders,dish));
            request.getSession().setAttribute(PARAM_ORDER_COST,receiver.addCost(orderCost,dish));
            page = ConfigurationManager.getProperty("path.page.main");
            router.setPagePath(page);
            router.setRoute(Router.RouteType.REDIRECT);
            LOGGER.log(Level.INFO,"Adding in the basket");
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e);
        }
    }
}
