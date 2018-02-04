package com.zarembin.epampjsp.resource;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("resource/message", new Locale("en", "US"))),
    RU(ResourceBundle.getBundle("resource/message", new Locale("ru", "RU")));
    private ResourceBundle bundle;



    /////////////////    NOT REQUEST
    MessageManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    public String getMessage(String key) {
        return bundle.getString(key);
    }

    public static MessageManager defineLocale(HttpServletRequest request){
        MessageManager messageManager = null;
        if (request.getSession().getAttribute("changeLanguage") == null){
            messageManager = messageManager.RU;
        }
        if ("en_US".equals(request.getSession().getAttribute("changeLanguage"))){
            messageManager = messageManager.EN;
        }
        else{
            messageManager = messageManager.RU;
        }
        return messageManager;
    }
}