package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class ReviewCommand implements ActionCommand {
    private static final String PARAM_REVIEWS = "reviews";
    private UserService receiver;

    public ReviewCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        try {
            request.getSession().setAttribute(PARAM_REVIEWS,receiver.findReviews());
        } catch (ServiceException e) {
            ////////    как ошибку отправлять Forfard или REDIRECT
            page = ConfigurationManager.getProperty("path.page.login");
            router.setPagePath(page);
            return router;
        }
        page = ConfigurationManager.getProperty("path.page.reviews");
        router.setPagePath(page);
        return router;
    }
}
