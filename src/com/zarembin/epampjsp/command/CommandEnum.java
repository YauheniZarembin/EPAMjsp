package com.zarembin.epampjsp.command;


import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.service.UserService;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand(new UserService());
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    SIGNUP{
        {
            this.command = new SignupCommand(new UserService());
        }
    },
    LOCALE{
        {
            this.command = new I18nCommand();
        }
    },
    TYPEOFDISH {
        {
            this.command = new MenuCommand(new MenuService());
        }
    },
    ADD_DISH{
        {
            this.command = new AddDishCommand(new MenuService());
        }
    },
    ADMIN_MENU{
        {
            this.command = new AdminMenuCommand(new MenuService());
        }
    },
    ADMIN_USERS{
        {
            this.command = new UserListCommand(new AdminService());
        }
    },
    ADMIN_ORDERS{
        {
            this.command = new AdminOrdersCommand(new AdminService());
        }
    };

    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
