package kz.epam.darling.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfileFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(ProfileFilter.class.getName());


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.sendRedirect(request.getContextPath() + "/main");
            } else {
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
