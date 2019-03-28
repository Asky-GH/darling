package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.*;
import kz.epam.darling.model.Gender;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.GenderDAO;
import kz.epam.darling.dao.LanguageDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GendersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GendersCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Gender> genders = GenderDAO.find();
        List<Language> languages = LanguageDAO.findAll();
        List<Integer> ids = GenderDAO.findIds();
        request.setAttribute(Attribute.GENDERS, genders);
        request.setAttribute(Attribute.LANGUAGES, languages);
        request.setAttribute(Attribute.IDS, ids);
        try {
            request.getRequestDispatcher(View.GENDERS).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Gender gender = new Gender();
        gender.setId(Integer.parseInt(request.getParameter(Parameter.ID)));
        gender.setLanguage(LanguageDAO.findById(Integer.parseInt(request.getParameter(Parameter.LANGUAGE_ID))));
        gender.setType(request.getParameter(Parameter.TYPE));
        String action = request.getParameter(Parameter.ACTION);
        switch (action) {
            case Action.CREATE:
                GenderDAO.create(gender);
                break;

            case Action.EDIT:
                GenderDAO.update(gender);
                break;
        }
        try {
            response.sendRedirect(request.getContextPath() + Route.GENDERS);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
