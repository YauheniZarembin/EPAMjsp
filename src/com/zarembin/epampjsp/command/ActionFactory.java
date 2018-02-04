package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        MessageManager messageManager = MessageManager.defineLocale(request);
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");
        System.out.println(action);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + messageManager.getMessage("message.wrongAction"));
        }
        return current;
    }
}