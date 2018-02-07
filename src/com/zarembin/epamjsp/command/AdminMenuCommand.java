package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.MenuService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class AdminMenuCommand implements ActionCommand{

    private MenuService receiver;
    private static final String PARAM_DISHES = "dishes";

    public AdminMenuCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        try {
            request.getSession().setAttribute(PARAM_DISHES,receiver.findDishesByType(TypeOfDish.SOUP));
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e);
        }
        page = ConfigurationManager.getProperty("path.page.adminMenu");
        router.setPagePath(page);
        return router;
    }
}
