package kz.epam.darling.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class.getName());


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String action = request.getServletPath();
        HttpSession session = request.getSession(false);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            switch (action) {
                case "/login":
                case "/registration":
                    if (session != null && session.getAttribute("user") != null) {
                        response.sendRedirect(request.getContextPath() + "/main");
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    break;

                case "/profile":
                case "/chat":
                    if (session == null || session.getAttribute("user") == null) {
                        response.sendRedirect(request.getContextPath() + "/login");
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    break;
            }
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void destroy() {

    }
}
