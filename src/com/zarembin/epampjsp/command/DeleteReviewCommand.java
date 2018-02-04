package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;


public class DeleteReviewCommand implements ActionCommand {

    private final static String PARAM_REVIEW_ID = "reviewId";
    private final static String PARAM_REVIEWS = "reviews";
    private static final String PARAM_USER = "user";
    private final static String PARAM_ERROR_MESSAGE ="MessageReview";

    private UserService receiver;

    public DeleteReviewCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        int reviewId = Integer.valueOf(request.getParameter(PARAM_REVIEW_ID));
        request.getSession().setAttribute(PARAM_ERROR_MESSAGE,null);

        try {
            receiver.deleteReview(reviewId);
            request.getSession().setAttribute(PARAM_REVIEWS, receiver.findReviews());
            page = ((user == null) || !user.isAdmin()) ? ConfigurationManager.getProperty("path.page.reviews") : ConfigurationManager.getProperty("path.page.adminReviews");
            router.setPagePath(page);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
