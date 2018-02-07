package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.service.AdminService;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeBanCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

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
            LOGGER.log(Level.INFO,"Change ban for "+userName);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(),e);
        }
        page = ConfigurationManager.getProperty("path.page.adminUsers");
        router.setPagePath(page);
        return router;
    }
}