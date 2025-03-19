package Controll.Auth;

import Model.DTO.Account;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/staff/*", "/user/*"})
public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("hello from filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        // Check if user has loggedIn
        Account acc = (session != null) ? (Account) session.getAttribute("acc") : null;
        String requestURI = req.getRequestURI();

        if (acc == null) {
            req.setAttribute("error", "You must log in first");
            req.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        // Check he/she is a staff or user
        boolean isStaff = acc.isIsStaff();
        if (requestURI.startsWith(req.getContextPath() + "/staff") && !isStaff) {
            req.setAttribute("error", "You cannot access these actions with role as a user!");
            req.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        } else if (requestURI.startsWith(req.getContextPath() + "/user") && isStaff) {
            req.setAttribute("error", "You cannot access these actions with role as a staff!");
            req.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}