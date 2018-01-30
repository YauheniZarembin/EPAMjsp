package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;
public interface ActionCommand {
    Router execute(HttpServletRequest request) throws CommandException;
}