package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String pagePath = ConfigurationManager.getProperty("path.page.error");
        router.setPagePath(pagePath);
        return router;
    }
}
