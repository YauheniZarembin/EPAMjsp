package com.zarembin.epampjsp.command;


import com.zarembin.epampjsp.service.LogInService;
import com.zarembin.epampjsp.service.SignUpService;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand(new LogInService());
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    SIGNUP{
        {

            this.command = new SignupCommand(new SignUpService());
        }
    },
    LOCALE{
        {
            this.command = new I18nCommand();
        }
    };


    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
