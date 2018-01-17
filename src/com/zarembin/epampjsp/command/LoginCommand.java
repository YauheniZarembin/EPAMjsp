package com.zarembin.epampjsp.command;
import com.zarembin.epampjsp.logic.UserReceiver;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private UserReceiver receiver;

    public LoginCommand(UserReceiver userReceiver){
        receiver = userReceiver;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        // извлечение из запроса логина и пароля
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        // проверка логина и пароля

        if (receiver.checkUser(login,pass)){
            request.setAttribute("user", login);
            page = ConfigurationManager.getProperty("path.page.main");
        }
        else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
