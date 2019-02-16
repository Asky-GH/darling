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
                    if (session != null && session.getAttribute("principal") != null) {
                        response.sendRedirect(request.getContextPath() + "/main");
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    break;

                case "/profile":
                    if (session == null || session.getAttribute("principal") == null) {
                        request.setAttribute("referer", request.getRequestURL());
                        request.getRequestDispatcher("jsp/auth/login.jsp").forward(request, response);
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    break;
                case "/chat":
                    if (session == null || session.getAttribute("principal") == null) {
                        request.setAttribute("referer", request.getRequestURL() + "?" + request.getQueryString());
                        request.getRequestDispatcher("jsp/auth/login.jsp").forward(request, response);
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    break;

                default:
                    filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void destroy() {

    }
}
