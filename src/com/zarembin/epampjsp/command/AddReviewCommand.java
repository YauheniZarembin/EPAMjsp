package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Review;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;
import com.zarembin.epampjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AddReviewCommand implements ActionCommand {

    private final static String PARAM_USER ="user";
    private final static String PARAM_MARK ="mark";
    private final static String PARAM_COMMENT ="comment";
    private final static String PARAM_REVIEWS ="reviews";
    private final static String PARAM_ERROR_MESSAGE ="Message";


    private UserService receiver;

    public AddReviewCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        String userName = ((User) request.getSession().getAttribute(PARAM_USER)).getUserName();
        int mark = Integer.valueOf(request.getParameter(PARAM_MARK));
        String textReview = request.getParameter(PARAM_COMMENT);
        MessageManager messageManager = MessageManager.defineLocale(request);
        InputTextValidator inputTextValidator = new InputTextValidator();
        request.getSession().setAttribute(PARAM_ERROR_MESSAGE,null);

        try {
            if(inputTextValidator.isCommentValid(textReview)) {
                receiver.insertReview(userName, mark, textReview);
            }
            else{
                request.getSession().setAttribute(PARAM_ERROR_MESSAGE,
                        messageManager.getMessage("message.reviewError"));
            }
            request.getSession().setAttribute(PARAM_REVIEWS, receiver.findReviews());
            page = ConfigurationManager.getProperty("path.page.reviews");
            router.setPagePath(page);
            router.setRoute(Router.RouteType.REDIRECT);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e.getCause());
        }
    }
}
