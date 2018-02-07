package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final String PARAM_WRONG_ACTION ="wrongAction";
    private static final String PARAM_COMMAND = "command";
    private static final String PARAM_LOCALE = "changeLanguage";

    public ActionCommand defineCommand(HttpServletRequest request) {
        MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute(PARAM_LOCALE));
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter(PARAM_COMMAND);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute(PARAM_WRONG_ACTION, action
                    + messageManager.getMessage("message.wrongAction"));
        }
        return current;
    }
}