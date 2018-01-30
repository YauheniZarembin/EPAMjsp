package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;
import com.zarembin.epampjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SAAJResult;

public class SignUpCommand implements ActionCommand{

    private static final String PARAM_USER_NAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_LASTNAME = "lastname";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_CARD_PASSWORD = "card number";
    private static final String PARAM_CARD_NUMBER = "card password";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_MESSAGE = "Message";

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
        String lastname = request.getParameter(PARAM_LASTNAME);
        String email = request.getParameter(PARAM_EMAIL);
        String cardNumber = request.getParameter(PARAM_CARD_NUMBER);
        String cardPassword = request.getParameter(PARAM_CARD_PASSWORD);
        MessageManager messageManager = MessageManager.defineLocale(request);

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
               throw new CommandException(e.getMessage(), e.getCause());
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
