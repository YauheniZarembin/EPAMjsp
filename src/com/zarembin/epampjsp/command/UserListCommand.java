package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class UserListCommand implements ActionCommand {

    private static final String PARAM_USERS = "users";
    private AdminService receiver;

    public UserListCommand(AdminService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        try {
            request.getSession().setAttribute(PARAM_USERS,receiver.findAllUsers());
            page = ConfigurationManager.getProperty("path.page.adminUsers");
            router.setPagePath(page);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
