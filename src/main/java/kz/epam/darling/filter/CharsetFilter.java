package kz.epam.darling.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(CharsetFilter.class.getName());


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            servletResponse.setCharacterEncoding("utf-8");
            servletRequest.setCharacterEncoding("utf-8");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (IOException | ServletException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void destroy() {

    }
}
