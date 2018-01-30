package com.zarembin.epampjsp.command;

import com.sun.deploy.net.HttpRequest;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class I18nCommand implements ActionCommand {
    private static final String REG_EX_JSP = "/jsp.+";
    private static final String PARAM_CHANGE_LANGUAGE = "changeLanguage";
    private static final String PARAM_PAGE_PATH = "pagePath";

    private UserService receiver;

    public I18nCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String locale = (String) request.getSession().getAttribute(PARAM_CHANGE_LANGUAGE);
        String page = request.getParameter(PARAM_PAGE_PATH);


        request.getSession().setAttribute(PARAM_CHANGE_LANGUAGE, receiver.changeLanguage(locale));
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(request.getContextPath()+receiver.returnSamePage(page,REG_EX_JSP));
        return router;
    }
}
