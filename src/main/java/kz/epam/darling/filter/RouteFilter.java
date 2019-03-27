package kz.epam.darling.filter;

import kz.epam.darling.constant.Attribute;
import kz.epam.darling.constant.Route;
import kz.epam.darling.constant.View;
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
        HttpSession session = request.getSession();
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            switch (action) {
                case Route.LOGIN:
                case Route.REGISTRATION:
                    if (session.getAttribute(Attribute.PRINCIPAL) != null) {
                        response.sendRedirect(request.getContextPath() + Route.MAIN);
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    break;

                case Route.PROFILE:
                case Route.MESSAGES:
                    if (session.getAttribute(Attribute.PRINCIPAL) == null) {
                        request.setAttribute(Attribute.REFERER, request.getRequestURL());
                        request.getRequestDispatcher(View.LOGIN).forward(request, response);
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    break;

                case Route.CHAT:
                    if (session.getAttribute(Attribute.PRINCIPAL) == null) {
                        request.setAttribute(Attribute.REFERER, request.getRequestURL() + "?" + request.getQueryString());
                        request.getRequestDispatcher(View.LOGIN).forward(request, response);
                    } else {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                    break;

                case Route.ADMIN:
                case Route.USERS:
                case Route.LANGUAGES:
                case Route.GENDERS:
                case Route.COUNTRIES:
                case Route.CITIES:
                    if (session.getAttribute(Attribute.PRINCIPAL) == null ||
                            !((User) session.getAttribute(Attribute.PRINCIPAL)).getRole().getType().equals("admin")) {
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
