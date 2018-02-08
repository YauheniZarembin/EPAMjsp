package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.dao.UtilDAO;
import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.resource.MessageManager;
import com.zarembin.epamjsp.service.UserService;
import com.zarembin.epamjsp.servlet.Router;
import com.zarembin.epamjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddReviewCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_USER ="user";
    private static final String PARAM_MARK ="mark";
    private static final String PARAM_COMMENT ="comment";
    private static final String PARAM_REVIEWS ="reviews";
    private static final String PARAM_ERROR_MESSAGE ="MessageReview";
    private static final String PARAM_LOCALE = "changeLanguage";


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
        MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute(PARAM_LOCALE));
        InputTextValidator inputTextValidator = new InputTextValidator();
        request.getSession().setAttribute(PARAM_ERROR_MESSAGE,null);

        try {
            if(inputTextValidator.isTextExist(textReview)) {
                receiver.insertReview(userName, mark, textReview);
                LOGGER.log(Level.INFO,"Adding new review");
            }
            else{
                request.getSession().setAttribute(PARAM_ERROR_MESSAGE,
                        messageManager.getMessage("message.reviewError"));
                LOGGER.log(Level.INFO,"Incorrect review");
            }
            request.getSession().setAttribute(PARAM_REVIEWS, receiver.findReviews());
            page = ConfigurationManager.getProperty("path.page.reviews");
            router.setPagePath(page);
            router.setRoute(Router.RouteType.REDIRECT);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e);
        }
    }
}
