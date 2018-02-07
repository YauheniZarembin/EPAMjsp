package com.zarembin.epamjsp.command;



import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_CHANGE_LANGUAGE = "changeLanguage";
    private static final String PARAM_USER = "user";

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User)request.getSession().getAttribute(PARAM_USER);
        Object locale = request.getSession().getAttribute(PARAM_CHANGE_LANGUAGE);

        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.index");
        request.getSession().invalidate();
        router.setPagePath(page);
        request.getSession().setAttribute(PARAM_CHANGE_LANGUAGE,locale);
        LOGGER.log(Level.INFO,user.getUserName()+" log out");
        return router;
    }
}
