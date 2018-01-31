package com.zarembin.epampjsp.filter;

import com.zarembin.epampjsp.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter( urlPatterns = { "/jsp/user/*" },
        initParams = { @WebInitParam(name = "ADMIN_MENU", value = "/jsp/admin/adminMenu.jsp")})
public class SecutityFilterUser implements Filter {
    private static final String PARAM_USER = "user";
    private String indexPathAdminMenu;
    public void init(FilterConfig fConfig) throws ServletException {
        indexPathAdminMenu = fConfig.getInitParameter("ADMIN_MENU");

    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        User user = (User)((HttpServletRequest) request).getSession().getAttribute(PARAM_USER);

        if (user == null || !user.isAdmin()){
            String path = ((HttpServletRequest) request).getRequestURI();
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(path);
            dispatcher.forward(request,response);
        }
        else{
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(indexPathAdminMenu);
            dispatcher.forward(request,response);
        }

        chain.doFilter(request, response);
    }
    public void destroy() {
    }

}
