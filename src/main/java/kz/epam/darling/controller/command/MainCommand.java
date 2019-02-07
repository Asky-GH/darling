package kz.epam.darling.controller.command;

import kz.epam.darling.model.City;
import kz.epam.darling.model.Country;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.CityDAO;
import kz.epam.darling.model.dao.CountryDAO;
import kz.epam.darling.model.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(MainCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("users") == null) {
            List<User> users = UserDAO.findAll();
            request.setAttribute("users", users);
        }
        List<Country> countries = CountryDAO.findAll();
        List<City> cities = CityDAO.findByCountryId(countries.get(0).getId());
        request.setAttribute("countries", countries);
        request.setAttribute("cities", cities);
        try {
            request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int genderId = Integer.parseInt(request.getParameter("genderId"));
        String fromAgeParam = request.getParameter("fromAge");
        int fromAge = fromAgeParam.isEmpty() ? 18 : Integer.parseInt(fromAgeParam);
        String toAgeParam = request.getParameter("toAge");
        int toAge = toAgeParam.isEmpty() ? 100 : Integer.parseInt(toAgeParam);
        int countryId = Integer.parseInt(request.getParameter("countryId"));
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        List<User> users = UserDAO.findByConstraints(genderId, LocalDate.now().minusYears(fromAge).getYear(),
                                                    LocalDate.now().minusYears(toAge).getYear(), countryId, cityId);
        request.setAttribute("users", users);
        doGet(request, response);
    }
}
