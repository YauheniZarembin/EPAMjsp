package com.zarembin.epamjsp.servlet;

import com.zarembin.epamjsp.command.ActionCommand;
import com.zarembin.epamjsp.command.ActionFactory;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.pool.ConnectionPool;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.resource.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;


@WebServlet("/controller")
public class Controller extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Router router;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        try {
            router = command.execute(request);
            MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute("changeLanguage"));
            if (router.getPagePath() != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(router.getPagePath());
                switch (router.getRoute()) {
                    case FORWARD:
                        dispatcher.forward(request, response);
                        break;
                    case REDIRECT:
                        response.sendRedirect(request.getContextPath() + router.getPagePath());
                        break;
                }
            } else {
                String page = ConfigurationManager.getProperty("path.page.index");
                request.setAttribute("nullPage",
                        messageManager.getMessage("message.nullPage"));
                response.sendRedirect(request.getContextPath() + page);
            }
        } catch (CommandException e) {
            request.setAttribute("exceptionMessage", e.getMessage());
            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.error")).forward(request, response);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}

