package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.AdminService;
import com.zarembin.epamjsp.servlet.Router;

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
            throw new CommandException(e.getMessage(), e);
        }
    }
}
