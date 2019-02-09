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
import java.util.ArrayList;
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
        boolean genderConstraint = true;
        boolean countryConstraint = true;
        boolean cityConstraint = true;

        int genderId = 0;
        try {
            genderId = Integer.parseInt(request.getParameter("genderId"));
        } catch (NumberFormatException | NullPointerException e) {
            genderConstraint = false;
        }

        int fromAge = 0;
        try {
            fromAge = Integer.parseInt(request.getParameter("fromAge"));
        } catch (NumberFormatException e) {
            fromAge = 18;
        }

        int toAge = 0;
        try {
            toAge = Integer.parseInt(request.getParameter("toAge"));
        } catch (NumberFormatException e) {
            toAge = 100;
        }

        int countryId = 0;
        try {
            countryId = Integer.parseInt(request.getParameter("countryId"));
        } catch (NumberFormatException e) {
            countryConstraint = false;
        }

        int cityId = 0;
        try {
            cityId = Integer.parseInt(request.getParameter("cityId"));
        } catch (NumberFormatException e) {
            cityConstraint = false;
        }

        int toYear = LocalDate.now().minusYears(fromAge).getYear();
        int fromYear = LocalDate.now().minusYears(toAge).getYear();
        List<User> users;
        if (genderConstraint) {
            if (countryConstraint) {
                if (cityConstraint) {
                    users = UserDAO.findByConstraints(genderId, toYear, fromYear, countryId, cityId, language.getId());
                } else {
                    users = UserDAO.findByGenderAndCountry(genderId, toYear, fromYear, countryId, language.getId());
                }
            } else {
                users = UserDAO.findByGender(genderId, toYear, fromYear, language.getId());
            }
        } else {
            if (countryConstraint) {
                if (cityConstraint) {
                    users = UserDAO.findByCountryAndCity(toYear, fromYear, countryId, cityId, language.getId());
                } else {
                    users = UserDAO.findByCountry(toYear, fromYear, countryId, language.getId());
                }
            } else {
                users = UserDAO.findByAge(toYear, fromYear, language.getId());
            }
        }

        request.setAttribute("users", users);
        doGet(request, response);
    }
}
