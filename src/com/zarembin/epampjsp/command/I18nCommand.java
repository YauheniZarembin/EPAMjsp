package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class I18nCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

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
        router.setPagePath(request.getContextPath()+receiver.returnSamePage(page));
        LOGGER.log(Level.INFO, "11111111111111");
        return router;
    }
}
