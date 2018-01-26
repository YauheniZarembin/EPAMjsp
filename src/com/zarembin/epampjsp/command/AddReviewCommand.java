package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.Review;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;

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
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String userName = ((User) request.getSession().getAttribute(PARAM_USER)).getUserName();
        int mark = Integer.valueOf(request.getParameter(PARAM_MARK));
        String textReview = request.getParameter(PARAM_COMMENT);
        MessageManager messageManager = MessageManager.defineLocale(request);

        try {
            if(!textReview.isEmpty()) {
                receiver.insertReview(userName, mark, textReview);
            }
            else{
                request.setAttribute(PARAM_ERROR_MESSAGE,
                        messageManager.getMessage("message.reviewerror"));
            }
            request.getSession().setAttribute(PARAM_REVIEWS, receiver.findReviews());
        } catch (ServiceException e) {
            ////////    как ошибку отправлять Forfard или REDIRECT
            /////    return в  catch  ?????????????????
            page = ConfigurationManager.getProperty("path.page.login");
            router.setPagePath(page);
            return router;
        }
        page = ConfigurationManager.getProperty("path.page.reviews");
        router.setPagePath(page);
        return router;
    }
}
