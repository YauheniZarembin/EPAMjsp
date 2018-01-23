package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.LogInService;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class MenuCommand implements ActionCommand {

    private MenuService receiver;
    private static final String PARAM_DISH_TYPE = "dishType";
    private static final String PARAM_DISHES = "dishes";

    public MenuCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        //System.out.println((String)request.getAttribute(PARAM_NAME_DISH_TYPE)).toUpperCase()))
        Router router = new Router();
        String page;
        TypeOfDish typeOfDish = TypeOfDish.valueOf((request.getParameter(PARAM_DISH_TYPE)).toUpperCase());
        try {
            request.getSession().setAttribute(PARAM_DISHES,receiver.findDishesByType(typeOfDish));
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
