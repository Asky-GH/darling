package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.*;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.LanguageDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LanguagesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LanguagesCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Language> languages = LanguageDAO.findAll();
        request.setAttribute(Attribute.LANGUAGES, languages);
        try {
            request.getRequestDispatcher(View.LANGUAGES).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Language language = new Language();
        language.setLocale(request.getParameter(Parameter.LOCALE));
        language.setName(request.getParameter(Parameter.NAME));
        String action = request.getParameter(Parameter.ACTION);
        switch (action) {
            case Action.CREATE:
                LanguageDAO.create(language);
                break;

            case Action.EDIT:
                language.setId(Integer.parseInt(request.getParameter(Parameter.ID)));
                LanguageDAO.update(language);
                break;
        }
        try {
            response.sendRedirect(request.getContextPath() + Route.LANGUAGES);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
