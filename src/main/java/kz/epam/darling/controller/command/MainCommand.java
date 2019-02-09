package kz.epam.darling.controller.command;

import kz.epam.darling.model.*;
import kz.epam.darling.model.dao.CityDAO;
import kz.epam.darling.model.dao.CountryDAO;
import kz.epam.darling.model.dao.GenderDAO;
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
        Language language = (Language) request.getAttribute("language");
        if (request.getAttribute("users") == null) {
            List<User> users = UserDAO.findAll(language.getId());
            request.setAttribute("users", users);
        }
        List<Gender> genders = GenderDAO.findAll(language.getId());
        List<Country> countries = CountryDAO.findAll(language.getId());
        request.setAttribute("genders", genders);
        request.setAttribute("countries", countries);
        try {
            request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        int genderId = Integer.parseInt(request.getParameter("genderId"));
        String fromAgeParam = request.getParameter("fromAge");
        int fromAge = fromAgeParam.isEmpty() ? 18 : Integer.parseInt(fromAgeParam);
        String toAgeParam = request.getParameter("toAge");
        int toAge = toAgeParam.isEmpty() ? 100 : Integer.parseInt(toAgeParam);
        int countryId = Integer.parseInt(request.getParameter("countryId"));
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        int toYear = LocalDate.now().minusYears(fromAge).getYear();
        int fromYear = LocalDate.now().minusYears(toAge).getYear();
        List<User> users = UserDAO.findByConstraints(genderId, toYear, fromYear, countryId, cityId, language.getId());
        request.setAttribute("users", users);
        doGet(request, response);
    }
}
