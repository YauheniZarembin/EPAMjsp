package com.zarembin.epampjsp.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)   	// 100 MB
public class FileUploadingServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "resource/image";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;


        File fileSaveDir = new File(uploadFilePath);
        System.out.println(fileSaveDir.getAbsolutePath());
        System.out.println(fileSaveDir.getPath());

        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        String fileName;
        int i = 0;
        for (Part part : request.getParts()) {
            i++;
            fileName = getFileName(part);
            System.out.println(fileName + "     "+String.valueOf(i));
            part.write(uploadFilePath + File.separator + fileName);
        }
    }



    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}