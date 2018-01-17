package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.command.ActionCommand;
import com.zarembin.epampjsp.command.LoginCommand;
import com.zarembin.epampjsp.command.LogoutCommand;
import com.zarembin.epampjsp.logic.UserReceiver;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand(new UserReceiver());
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
