package com.zarembin.epamjsp.command;

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

public class SignUpCommand implements ActionCommand{

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_USER_NAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_LAST_NAME = "lastname";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_CARD_PASSWORD = "card number";
    private static final String PARAM_CARD_NUMBER = "card password";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_MESSAGE = "Message";
    private static final String PARAM_LOCALE = "changeLanguage";

    private UserService receiver;

    public SignUpCommand(UserService userReceiver){
        receiver = userReceiver;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page = null;
        String userName = request.getParameter(PARAM_USER_NAME);
        String password = request.getParameter(PARAM_PASSWORD);
        String name = request.getParameter(PARAM_NAME);
        String lastname = request.getParameter(PARAM_LAST_NAME);
        String email = request.getParameter(PARAM_EMAIL);
        String cardNumber = request.getParameter(PARAM_CARD_NUMBER);
        String cardPassword = request.getParameter(PARAM_CARD_PASSWORD);
        MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute(PARAM_LOCALE));

        InputTextValidator inputTextValidator = new InputTextValidator();
        if (inputTextValidator.isPasswordValid(password)&& inputTextValidator.isLogInValid(userName)
               && inputTextValidator.isEmailValid(email) && inputTextValidator.isCardNumberValid(cardNumber)) {
           try {
               if(!receiver.isSuchUserExist(userName)){
                   if (receiver.isSuchCardExist(cardNumber,cardPassword)){
                       receiver.singUpUserByEncryption(userName,password,name,lastname,email,cardNumber);
                       request.getSession().setAttribute(PARAM_MESSAGE,
                               messageManager.getMessage("message.signUpSuccess"));
                       page = ConfigurationManager.getProperty("path.page.login");
                       router.setRoute(Router.RouteType.REDIRECT);
                       LOGGER.log(Level.INFO,userName+" sign up");
                   }
                   else{
                       request.setAttribute(PARAM_ERROR_MESSAGE,
                               messageManager.getMessage("message.cardNotExist"));
                       page = ConfigurationManager.getProperty("path.page.signup");
                   }
               }
               else{
                   request.setAttribute(PARAM_ERROR_MESSAGE,
                           messageManager.getMessage("message.userExist"));
                   page = ConfigurationManager.getProperty("path.page.signup");
               }
           } catch (ServiceException e) {
               throw new CommandException(e.getMessage(),e);
           }
       }
        else {
            request.setAttribute(PARAM_ERROR_MESSAGE,
                    messageManager.getMessage("message.signUpError"));
            page = ConfigurationManager.getProperty("path.page.signup");
        }

        router.setPagePath(page);
        return router;

    }
}
