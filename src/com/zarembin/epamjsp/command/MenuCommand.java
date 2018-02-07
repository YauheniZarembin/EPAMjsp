package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.MenuService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class MenuCommand implements ActionCommand {

    private MenuService receiver;
    private static final String PARAM_USER = "user";
    private static final String PARAM_DISH_TYPE = "dishType";
    private static final String PARAM_DISHES = "dishes";

    public MenuCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        TypeOfDish typeOfDish = TypeOfDish.valueOf((request.getParameter(PARAM_DISH_TYPE)).toUpperCase());
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        try {
            request.getSession().setAttribute(PARAM_DISHES,receiver.findDishesByType(typeOfDish));
            page = (user == null || !user.isAdmin()) ? ConfigurationManager.getProperty("path.page.main") : ConfigurationManager.getProperty("path.page.adminMenu");
            router.setPagePath(page);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
