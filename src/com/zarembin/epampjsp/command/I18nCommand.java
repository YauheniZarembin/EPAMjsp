package com.zarembin.epampjsp.command;

import com.sun.deploy.net.HttpRequest;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class I18nCommand implements ActionCommand {
    private final String REG_EX_JSP = "/jsp.+";
    private static final String PARAM_CHANGE_LANGUAGE = "changeLanguage";
    private static final String PARAM_PAGE_PATH = "pagePath";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        if (request.getSession().getAttribute(PARAM_CHANGE_LANGUAGE) == null){
            request.getSession().setAttribute(PARAM_CHANGE_LANGUAGE , "ru_RU");
        }
        if ("en_US".equals(request.getSession().getAttribute(PARAM_CHANGE_LANGUAGE))){
            request.getSession().setAttribute(PARAM_CHANGE_LANGUAGE , "ru_RU");
        }
        else{
            request.getSession().setAttribute(PARAM_CHANGE_LANGUAGE , "en_US");
        }


        String page = null;
        Pattern p = Pattern.compile(REG_EX_JSP);
        Matcher m = p.matcher(request.getParameter(PARAM_PAGE_PATH));
        if(m.find()){
            page = m.group();
        }
        router.setPagePath(request.getContextPath()+page);
        return router;
    }
}
