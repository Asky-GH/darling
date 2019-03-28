package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.*;
import kz.epam.darling.model.Country;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.CountryDAO;
import kz.epam.darling.dao.LanguageDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CountriesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CountriesCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Country> countries = CountryDAO.find();
        List<Language> languages = LanguageDAO.findAll();
        List<Integer> ids = CountryDAO.findIds();
        request.setAttribute(Attribute.COUNTRIES, countries);
        request.setAttribute(Attribute.LANGUAGES, languages);
        request.setAttribute(Attribute.IDS, ids);
        try {
            request.getRequestDispatcher(View.COUNTRIES).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Country country = new Country();
        country.setId(Integer.parseInt(request.getParameter(Parameter.ID)));
        country.setLanguage(LanguageDAO.findById(Integer.parseInt(request.getParameter(Parameter.LANGUAGE_ID))));
        country.setName(request.getParameter(Parameter.NAME));
        String action = request.getParameter(Parameter.ACTION);
        switch (action) {
            case Action.CREATE:
                CountryDAO.create(country);
                break;

            case Action.EDIT:
                CountryDAO.update(country);
                break;
        }
        try {
            response.sendRedirect(request.getContextPath() + Route.COUNTRIES);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
