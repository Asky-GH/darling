package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.*;
import kz.epam.darling.model.City;
import kz.epam.darling.model.Country;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.CityDAO;
import kz.epam.darling.dao.CountryDAO;
import kz.epam.darling.dao.LanguageDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CitiesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CitiesCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<City> cities = CityDAO.findAll();
        List<Language> languages = LanguageDAO.findAll();
        List<Country> countries = CountryDAO.findAll(Constant.DEFAULT_LANGUAGE_ID);
        List<Integer> ids = CityDAO.findIds();
        request.setAttribute(Attribute.CITIES, cities);
        request.setAttribute(Attribute.LANGUAGES, languages);
        request.setAttribute(Attribute.COUNTRIES, countries);
        request.setAttribute(Attribute.IDS, ids);
        try {
            request.getRequestDispatcher(View.CITIES).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        City city = new City();
        city.setId(Integer.parseInt(request.getParameter(Parameter.ID)));
        int languageId = Integer.parseInt(request.getParameter(Parameter.LANGUAGE_ID));
        city.setLanguage(LanguageDAO.findById(languageId));
        city.setName(request.getParameter(Parameter.NAME));
        city.setCountry(CountryDAO.findById(Integer.parseInt(request.getParameter(Parameter.COUNTRY_ID)), languageId));
        String action = request.getParameter(Parameter.ACTION);
        switch (action) {
            case Action.CREATE:
                CityDAO.create(city);
                break;

            case Action.EDIT:
                CityDAO.update(city);
                break;
        }
        try {
            response.sendRedirect(request.getContextPath() + Route.CITIES);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
