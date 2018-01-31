package com.zarembin.epampjsp.filter;

import com.zarembin.epampjsp.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter( urlPatterns = { "/jsp/user/login/*" },
        initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") ,
                       @WebInitParam(name = "ADMIN_MENU", value = "/jsp/admin/adminMenu.jsp")})
public class SecurityFilterLoginUser implements Filter{
    private static final String PARAM_USER = "user";
    private String indexPathGuest;
    private String indexPathAdminMenu;
    public void init(FilterConfig fConfig) throws ServletException {
        indexPathGuest = fConfig.getInitParameter("INDEX_PATH");
        indexPathAdminMenu = fConfig.getInitParameter("ADMIN_MENU");

    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        User user = (User)((HttpServletRequest) request).getSession().getAttribute(PARAM_USER);
        if(user == null){
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(indexPathGuest);
            dispatcher.forward(request,response);
        }
        else if(user.isAdmin()){
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(indexPathAdminMenu);
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
