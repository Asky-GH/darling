package kz.epam.darling.command;

import kz.epam.darling.constant.Attribute;
import kz.epam.darling.constant.Parameter;
import kz.epam.darling.constant.Route;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.User;
import kz.epam.darling.dao.LanguageDAO;
import kz.epam.darling.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LanguageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LanguageCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int languageId = Integer.parseInt(request.getParameter(Parameter.LANGUAGE_ID));
        Language language = LanguageDAO.findById(languageId);
        User user = (User) request.getSession().getAttribute(Attribute.PRINCIPAL);
        if (user != null) {
            user = UserDAO.findById(user.getId(), languageId);
            request.getSession().setAttribute(Attribute.PRINCIPAL, user);
        }
        request.getSession().setAttribute(Attribute.LANGUAGE, language);
        try {
            String[] requestURI = request.getParameter(Parameter.REFERER).split("/");
            String requestQuery = request.getParameter(Parameter.QUERY);
            String action = requestURI[requestURI.length - 1].split("\\.")[0];
            if (requestQuery.isEmpty()) {
                response.sendRedirect(request.getContextPath() + Route.INDEX + action);
            } else {
                response.sendRedirect(request.getContextPath() + Route.INDEX + action + "?" + requestQuery);
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
