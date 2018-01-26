package com.zarembin.epampjsp.command;
import com.zarembin.epampjsp.entity.Dish;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;
import com.zarembin.epampjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_USER = "user";
    private static final String PARAM_USER_NAME = "userName";
    private static final String PARAM_MESSAGE = "Message";
    private UserService receiver;

    public LoginCommand(UserService userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String login = request.getParameter(PARAM_LOGIN);
        String pass = request.getParameter(PARAM_PASSWORD);
        MessageManager messageManager = MessageManager.defineLocale(request);


        InputTextValidator inputTextValidator = new InputTextValidator();
        if (inputTextValidator.isLogInValid(login) && inputTextValidator.isPasswordValid(pass)) {
            try {
                User user = receiver.findUserByEncryption(login, pass);
                if (user != null) {
                    if(user.isBan()){
                        request.setAttribute(PARAM_MESSAGE,
                                messageManager.getMessage("message.youareban"));
                        page = ConfigurationManager.getProperty("path.page.login");
                    }
                    else if(user.isAdmin()){
                        page = ConfigurationManager.getProperty("path.page.admin");
                    }
                    else {
                        //List<Dish> orders = new ArrayList<>();
                        //request.getSession().setAttribute("orders", orders);
                        request.getSession().setAttribute(PARAM_USER_NAME, user.getUserName());
                        request.getSession().setAttribute(PARAM_USER, user);
                        page = ConfigurationManager.getProperty("path.page.main");
                    }
                } else {
                    request.setAttribute(PARAM_MESSAGE,
                            messageManager.getMessage("message.loginerror"));
                    page = ConfigurationManager.getProperty("path.page.login");
                }
            } catch (ServiceException e) {
                request.setAttribute(PARAM_MESSAGE,
                        messageManager.getMessage("message.loginerror"));
                page = ConfigurationManager.getProperty("path.page.login");
            }
        } else {
            request.setAttribute(PARAM_MESSAGE,
                    messageManager.getMessage("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }

        router.setPagePath(page);
        return router;
    }

}