package kz.epam.darling.filter;

import kz.epam.darling.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RouteFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(RouteFilter.class.getName());


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
                case "/messages":
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

                case "/admin":
                case "/admin/users":
                case "/admin/languages":
                case "/admin/genders":
                case "/admin/countries":
                case "/admin/cities":
                    if (session == null || session.getAttribute("principal") == null ||
                            !((User) session.getAttribute("principal")).getRole().getType().equals("admin")) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
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
