package com.zarembin.epampjsp.command;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.LogInService;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.service.UserListService;
import com.zarembin.epampjsp.servlet.Router;
import com.zarembin.epampjsp.validator.InputTextValidator;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_USER_TYPE = "userType";
    private static final String PARAM_NAME_USER = "user";
    private static final String PARAM_NAME_MESSAGE = "Message";
    private LogInService receiver;

    public LoginCommand(LogInService userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        MessageManager messageManager = MessageManager.defineLocale(request);


        InputTextValidator inputTextValidator = new InputTextValidator();
        if (inputTextValidator.isLogInValid(login) && inputTextValidator.isPasswordValid(pass)) {
            try {
                User user = receiver.findUserByEncryption(login, pass);
                if (user != null ) {
                    MenuService menuService = new MenuService();
                    request.getSession().setAttribute("dishes", menuService.findDishesByType(TypeOfDish.SOUP));
                    request.getSession().setAttribute(PARAM_NAME_USER, user.getName()+" "+user.getLastname());
                    page = ConfigurationManager.getProperty("path.page.main");
                } else {
                    request.setAttribute(PARAM_NAME_MESSAGE,
                            messageManager.getMessage("message.loginerror"));
                    page = ConfigurationManager.getProperty("path.page.login");
                }
            } catch (ServiceException e) {
                request.setAttribute(PARAM_NAME_MESSAGE,
                        messageManager.getMessage("message.loginerror"));
                page = ConfigurationManager.getProperty("path.page.login");
            }
        } else {
            request.setAttribute(PARAM_NAME_MESSAGE,
                    messageManager.getMessage("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }

        router.setPagePath(page);
        return router;
    }

}