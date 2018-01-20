package com.zarembin.epampjsp.command;



import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.index");
        request.getSession().invalidate();

        router.setPagePath(page);
        return router;
    }
}
