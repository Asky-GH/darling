package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.model.City;
import kz.epam.darling.model.Country;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.CityDAO;
import kz.epam.darling.dao.CountryDAO;
import kz.epam.darling.dao.LanguageDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CitiesCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<City> cities = CityDAO.findAll();
        List<Language> languages = LanguageDAO.findAll();
        List<Country> countries = CountryDAO.findAll(1);
        List<Integer> ids = CityDAO.findIds();
        request.setAttribute("cities", cities);
        request.setAttribute("languages", languages);
        request.setAttribute("countries", countries);
        request.setAttribute("ids", ids);
        try {
            request.getRequestDispatcher("/jsp/admin/cities.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        City city = new City();
        city.setId(Integer.parseInt(request.getParameter("id")));
        int languageId = Integer.parseInt(request.getParameter("languageId"));
        city.setLanguage(LanguageDAO.findById(languageId));
        city.setName(request.getParameter("name"));
        city.setCountry(CountryDAO.findById(Integer.parseInt(request.getParameter("countryId")), languageId));
        String action = request.getParameter("action");
        switch (action) {
            case "create":
                CityDAO.create(city);
                break;

            case "edit":
                CityDAO.update(city);
                break;
        }
        try {
            response.sendRedirect(request.getContextPath() + "/admin/cities");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
