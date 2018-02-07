package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.UserService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DeleteReviewCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_REVIEW_ID = "reviewId";
    private static final String PARAM_REVIEWS = "reviews";
    private static final String PARAM_USER = "user";
    private static final String PARAM_ERROR_MESSAGE ="MessageReview";

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
            page = ((user == null) || !user.isAdmin()) ? ConfigurationManager.getProperty("path.page.reviews") :
                    ConfigurationManager.getProperty("path.page.adminReviews");
            LOGGER.log(Level.INFO,"Deleting review by  "+user.getUserName());
            router.setPagePath(page);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
