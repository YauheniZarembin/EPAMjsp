package com.zarembin.epampjsp.command;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class ChangeBanCommand implements ActionCommand {

    private static final String PARAM_USER_NAME = "editUserName";
    private static final String PARAM_USER_BAN = "editUserBan";
    private static final String PARAM_USERS = "users";

    private AdminService receiver;

    public ChangeBanCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        String userName = request.getParameter(PARAM_USER_NAME);
        String userBan = request.getParameter(PARAM_USER_BAN);
        try {
            receiver.changeUserBan(userName , new Boolean(userBan));
            request.getSession().setAttribute(PARAM_USERS, receiver.findAllUsers());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        page = ConfigurationManager.getProperty("path.page.adminUsers");
        router.setPagePath(page);
        return router;
    }
}