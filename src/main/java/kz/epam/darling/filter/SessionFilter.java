package kz.epam.darling.filter;

import kz.epam.darling.constant.Attribute;
import kz.epam.darling.constant.Constant;
import kz.epam.darling.dao.LanguageDAO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
            session.setAttribute(Attribute.LANGUAGE, LanguageDAO.findById(Constant.DEFAULT_LANGUAGE_ID));
        }
        request.setAttribute(Attribute.LANGUAGE, session.getAttribute(Attribute.LANGUAGE));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
