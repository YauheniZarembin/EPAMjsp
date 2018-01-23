//package com.zarembin.epampjsp.client;
//
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@WebFilter(urlPatterns = { "/controller" }, servletNames = { "MainServlet" })
//public class ServletSecurityFilter implements Filter {
//    public void destroy() {
//    }
//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        HttpSession session = req.getSession();
//        boolean isAdmin = (boolean) session.getAttribute("userType");
//        if (isAdmin) {
//            RequestDispatcher dispatcher = request.getServletContext()
//                    .getRequestDispatcher("/jsp/admin.jsp");
//            dispatcher.forward(req, resp);
//        }
//        else {
//            RequestDispatcher dispatcher = request.getServletContext()
//                    .getRequestDispatcher("/jsp/main.jsp");
//            dispatcher.forward(req, resp);
//        }
//// pass the request along the filter chain
//        chain.doFilter(request, response);
//    }
//    public void init(FilterConfig fConfig) throws ServletException {
//    }
//}
