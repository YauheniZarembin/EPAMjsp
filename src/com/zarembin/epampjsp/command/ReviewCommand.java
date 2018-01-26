package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class ReviewCommand implements ActionCommand {

    private static final String PARAM_USER = "user";
    private static final String PARAM_REVIEWS = "reviews";
    private UserService receiver;

    public ReviewCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        try {
            request.getSession().setAttribute(PARAM_REVIEWS,receiver.findReviews());
        } catch (ServiceException e) {
            ////////    как ошибку отправлять Forfard или REDIRECT
            page = ConfigurationManager.getProperty("path.page.login");
            router.setPagePath(page);
            return router;
        }


        page = ((user == null) || !user.isAdmin()) ? ConfigurationManager.getProperty("path.page.reviews") : ConfigurationManager.getProperty("path.page.adminReviews");
        router.setPagePath(page);
        return router;
    }
}
