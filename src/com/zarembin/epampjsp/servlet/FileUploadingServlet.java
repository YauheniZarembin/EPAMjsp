
package com.zarembin.epampjsp.servlet;

import com.zarembin.epampjsp.command.ActionCommand;
import com.zarembin.epampjsp.command.ActionFactory;
import com.zarembin.epampjsp.command.ChangePictureCommand;
import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.MenuService;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	            // 50 MB
        maxRequestSize=1024*1024*100)   	        // 100 MB
public class FileUploadingServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "resource/image";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;


        String fileName = null;
        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            part.write(uploadFilePath + File.separator + fileName);
            break;
        }

        ActionCommand command = new ChangePictureCommand(fileName, new MenuService());
        MessageManager messageManager = MessageManager.defineLocale(request);
        try {
            Router router = command.execute(request);
            if (router.getPagePath() != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(router.getPagePath());
                dispatcher.forward(request, response);
            } else {
                String page = ConfigurationManager.getProperty("path.page.index");
                request.getSession().setAttribute("nullPage",
                        messageManager.getMessage("message.nullPage"));
                response.sendRedirect(request.getContextPath() + page);
            }
        } catch (CommandException e) {
            request.getSession().setAttribute("exceptionCause", e.getCause().toString());
            request.getSession().setAttribute("exceptionMessage", e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }


    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= " + contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
