package com.zarembin.epamjsp.command;
import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.resource.MessageManager;
import com.zarembin.epamjsp.service.MenuService;
import com.zarembin.epamjsp.service.UserService;
import com.zarembin.epamjsp.servlet.Router;
import com.zarembin.epamjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_USER = "user";
    private static final String PARAM_USER_NAME = "userName";
    private static final String PARAM_MESSAGE = "Message";
    private static final String PARAM_DISHES = "dishes";
    private static final String PARAM_LOCALE = "changeLanguage";
    private UserService receiver;

    public LoginCommand(UserService userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        String login = request.getParameter(PARAM_LOGIN);
        String pass = request.getParameter(PARAM_PASSWORD);
        MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute(PARAM_LOCALE));
        request.getSession().setAttribute(PARAM_MESSAGE, null);


        InputTextValidator inputTextValidator = new InputTextValidator();
        if (inputTextValidator.isLogInValid(login) && inputTextValidator.isPasswordValid(pass)) {
            try {
                User user = receiver.findUserByEncryption(login, pass);
                if (user != null) {
                    if(user.isBan()){
                        request.setAttribute(PARAM_MESSAGE,
                                messageManager.getMessage("message.youAreBan"));
                        page = ConfigurationManager.getProperty("path.page.login");
                    }
                    else if(user.isAdmin()){
                        request.getSession().setAttribute(PARAM_USER, user);
                        request.getSession().setAttribute(PARAM_DISHES,new MenuService().findDishesByType(TypeOfDish.SOUP));
                        page = ConfigurationManager.getProperty("path.page.adminMenu");
                        LOGGER.log(Level.INFO, user.getUserName()+" log in");
                    }
                    else {
                        request.getSession().setAttribute(PARAM_DISHES,new MenuService().findDishesByType(TypeOfDish.SOUP));
                        request.getSession().setAttribute(PARAM_USER_NAME, user.getUserName());
                        request.getSession().setAttribute(PARAM_USER, user);
                        page = ConfigurationManager.getProperty("path.page.main");
                        LOGGER.log(Level.INFO, user.getUserName()+" log in");
                    }
                } else {
                    request.setAttribute(PARAM_MESSAGE,
                            messageManager.getMessage("message.loginError"));
                    page = ConfigurationManager.getProperty("path.page.login");
                }
            } catch (ServiceException e) {
                throw new CommandException(e.getMessage(), e);
            }
        } else {
            request.setAttribute(PARAM_MESSAGE,
                    messageManager.getMessage("message.loginError"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        router.setPagePath(page);
        return router;
    }

}