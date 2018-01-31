package com.zarembin.epampjsp.filter;

import com.zarembin.epampjsp.entity.User;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter( urlPatterns = { "/jsp/admin/*" },
             initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") ,
                            @WebInitParam(name = "MAIN_PATH", value = "/jsp/user/main.jsp")})
public class SecurityFilterAdmin implements Filter {
    private static final String PARAM_USER = "user";
    private String indexPathGuest;
    private String indexPathUser;
    public void init(FilterConfig fConfig) throws ServletException {
        indexPathGuest = fConfig.getInitParameter("INDEX_PATH");
        indexPathUser = fConfig.getInitParameter("MAIN_PATH");

    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        User user = (User)((HttpServletRequest) request).getSession().getAttribute(PARAM_USER);
        if(user == null){
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(indexPathGuest);
            dispatcher.forward(request,response);
        }
        else if(!user.isAdmin()){
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(indexPathUser);
            dispatcher.forward(request,response);
        }
        else{
            String path = ((HttpServletRequest) request).getRequestURI();
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(path);
            dispatcher.forward(request,response);
        }
        chain.doFilter(request, response);
    }
    public void destroy() {
    }
}