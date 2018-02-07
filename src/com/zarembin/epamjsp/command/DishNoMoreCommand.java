package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.MenuService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DishNoMoreCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_CHOSEN_DISH = "chosenDish";
    private static final String PARAM_TYPE_OF_DISH = "typeChosenDish";
    private static final  String PARAM_DISHES ="dishes";

    private MenuService receiver;

    public DishNoMoreCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        String dishName = request.getParameter(PARAM_CHOSEN_DISH);
        TypeOfDish typeOfDish = TypeOfDish.valueOf(request.getParameter(PARAM_TYPE_OF_DISH));
        try {
            receiver.deleteFromMenu(dishName);
            LOGGER.log(Level.INFO,"Change isNOmore for dish");
            request.getSession().setAttribute(PARAM_DISHES, receiver.findDishesByType(typeOfDish));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
        page = ConfigurationManager.getProperty("path.page.adminMenu");
        router.setPagePath(page);
        return router;
    }
}
