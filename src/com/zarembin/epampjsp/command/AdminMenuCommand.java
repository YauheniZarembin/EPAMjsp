package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class AdminMenuCommand implements ActionCommand{

    private MenuService receiver;
    private static final String PARAM_DISHES = "dishes";

    public AdminMenuCommand(MenuService receiver) {
        this.receiver = receiver;
    }


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try {
            request.getSession().setAttribute(PARAM_DISHES,receiver.findAllDishes());
        } catch (ServiceException e) {
            ////////    как ошибку отправлять Forfard или REDIRECT
            page = ConfigurationManager.getProperty("path.page.login");
            router.setPagePath(page);
            return router;
        }

        page = ConfigurationManager.getProperty("path.page.adminMenu");
        router.setPagePath(page);
        return router;
    }
}
