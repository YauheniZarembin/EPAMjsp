package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.command.ActionCommand;
import com.zarembin.epampjsp.command.LoginCommand;
import com.zarembin.epampjsp.command.LogoutCommand;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
