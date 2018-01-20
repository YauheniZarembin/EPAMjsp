package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.SignUpService;
import com.zarembin.epampjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;

public class SignupCommand implements ActionCommand{

    private static final String PARAM_USER_NAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_LASTNAME = "lastname";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_CARD_NUMBER = "card number";

    private SignUpService receiver;

    public SignupCommand(SignUpService userReceiver){
        receiver = userReceiver;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String username = request.getParameter(PARAM_USER_NAME);
        String password = request.getParameter(PARAM_PASSWORD);
        String name = request.getParameter(PARAM_NAME);
        String lastname = request.getParameter(PARAM_LASTNAME);
        String email = request.getParameter(PARAM_EMAIL);
        String cardNumber = request.getParameter(PARAM_CARD_NUMBER);


       if (receiver.checkUserData(username, password, name, lastname, email, cardNumber)) {
           try {
               if (receiver.singUpUserByEncryption(username, password, name, lastname, email, cardNumber)) {
                   request.setAttribute("Message",
                           MessageManager.getProperty("message.signupsucces"));
                   page = ConfigurationManager.getProperty("path.page.login");
               } else {
                   request.setAttribute("errorMessage",
                           MessageManager.getProperty("message.signuperror"));
                   page = ConfigurationManager.getProperty("path.page.signup");
               }
           } catch (ServiceException e) {
               request.setAttribute("errorMessage",
                       MessageManager.getProperty("message.signuperror"));
               page = ConfigurationManager.getProperty("path.page.signup");
           }
       }
        else {
            request.setAttribute("errorMessage",
                    MessageManager.getProperty("message.signuperror"));
            page = ConfigurationManager.getProperty("path.page.signup");
        }

        return page;

    }
}
