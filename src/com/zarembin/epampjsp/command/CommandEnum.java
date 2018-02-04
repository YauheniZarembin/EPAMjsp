package com.zarembin.epampjsp.command;


import com.zarembin.epampjsp.service.AdminService;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.service.UserService;

import java.awt.*;

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
            this.command = new SignUpCommand(new UserService());
        }
    },
    LOCALE{
        {
            this.command = new I18nCommand(new UserService());
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
    DISH_NO_MORE{
        {
            this.command = new DishNoMoreCommand(new MenuService());
        }
    },
    ADD_REVIEW{
        {
            this.command = new AddReviewCommand(new UserService());
        }
    },
    DELETE_REVIEW{
        {
            this.command = new DeleteReviewCommand(new UserService());
        }
    },
    DELETE_DISH{
        {
            this.command = new DeleteDishCommand(new MenuService());
        }
    },
    ADMIN_MENU{
        {
            this.command = new AdminMenuCommand(new MenuService());
        }
    },
    ADMIN_ADD_DISH{
        {
            this.command = new AdminAddDishCommand(new MenuService());
        }
    },
    ADMIN_USERS{
        {
            this.command = new UserListCommand(new AdminService());
        }
    },
    ADMIN_ORDER{
       {
            this.command = new AdminOrdersCommand(new AdminService());
        }
    },
    DELETE_ORDER {
        {
            this.command = new DeleteOrderCommand(new AdminService());
        }
    },
    DELETE_PAST_ORDERS {
        {
            this.command = new DeletePastOrderCommand(new AdminService());
        }
    },
    USER_ORDERS {
        {
            this.command = new UserOrdersCommand(new UserService());
        }
    },
    REVIEWS_COMMAND{
        {
            this.command = new ReviewCommand(new UserService());
        }
    },
    ORDERING{
        {
            this.command = new OrderingCommand(new UserService());
        }
    },
    TOP_UP{
        {
            this.command = new TopUpCommand(new UserService());
        }
    },
    CHANGE_BAN{
        {
            this.command = new ChangeBanCommand(new AdminService());
        }
    },
    EDIT_DISH_PRICE{
        {
            this.command = new EditDishPriceCommand(new MenuService());
        }
    };



    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
