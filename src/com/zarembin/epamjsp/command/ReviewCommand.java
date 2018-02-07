package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.UserService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;


public class ReviewCommand implements ActionCommand {

    private static final String PARAM_USER = "user";
    private static final String PARAM_REVIEWS = "reviews";
    private static final String PARAM_ERROR_MESSAGE ="MessageReview";

    private UserService receiver;

    public ReviewCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        try {
            request.getSession().setAttribute(PARAM_REVIEWS,receiver.findReviews());
            request.getSession().setAttribute(PARAM_ERROR_MESSAGE,null);
            page = ((user == null) || !user.isAdmin()) ? ConfigurationManager.getProperty("path.page.reviews") : ConfigurationManager.getProperty("path.page.adminReviews");
            router.setPagePath(page);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
