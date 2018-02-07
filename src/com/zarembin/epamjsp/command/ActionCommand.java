package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
public interface ActionCommand {
    Router execute(HttpServletRequest request) throws CommandException;
}