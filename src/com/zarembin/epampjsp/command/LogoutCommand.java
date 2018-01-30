package com.zarembin.epampjsp.command;



import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    private static final String PARAM_CHANGE_LANGUAGE = "changeLanguage";
    @Override
    public Router execute(HttpServletRequest request) {

        Object locale = request.getSession().getAttribute(PARAM_CHANGE_LANGUAGE);
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.index");
        request.getSession().invalidate();

        router.setPagePath(page);
        request.getSession().setAttribute(PARAM_CHANGE_LANGUAGE,locale);
        return router;
    }
}
