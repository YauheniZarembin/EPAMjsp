package com.zarembin.epampjsp.command;



import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        /* в случае ошибки или прямого обращения к контроллеру
         * переадресация на страницу ввода логина */
        Router router = new Router();
        String pagePath = ConfigurationManager.getProperty("path.page.login");
        router.setPagePath(pagePath);
        return router;
    }
}
