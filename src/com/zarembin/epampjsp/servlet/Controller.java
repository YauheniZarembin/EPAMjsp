package com.zarembin.epampjsp.servlet;

import com.zarembin.epampjsp.command.ActionCommand;
import com.zarembin.epampjsp.command.ActionFactory;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        Router router;

        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        router = command.execute(request);
        MessageManager messageManager = MessageManager.defineLocale(request);
        // метод возвращает страницу ответа
        //page = null; // поэксперементировать!
        if (router.getPagePath() != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(router.getPagePath());
            switch (router.getRoute()){
                case FORWARD:
                    dispatcher.forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(request.getContextPath()+router.getPagePath());
                    break;
            }
        } else {
            String page = ConfigurationManager. getProperty("path.page.index");
            request.getSession().setAttribute("nullPage",
                    messageManager.getMessage("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}

