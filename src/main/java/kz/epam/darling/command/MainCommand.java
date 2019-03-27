package kz.epam.darling.command;

import kz.epam.darling.constant.Attribute;
import kz.epam.darling.constant.Constant;
import kz.epam.darling.constant.Parameter;
import kz.epam.darling.constant.View;
import kz.epam.darling.model.Country;
import kz.epam.darling.model.Gender;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.User;
import kz.epam.darling.dao.CountryDAO;
import kz.epam.darling.dao.GenderDAO;
import kz.epam.darling.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MainCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(MainCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
        if (request.getAttribute(Attribute.USERS) == null) {
            List<User> users = UserDAO.findAll(language.getId());
            request.setAttribute(Attribute.USERS, users);
        }
        List<Gender> genders = GenderDAO.findAll(language.getId());
        List<Country> countries = CountryDAO.findAll(language.getId());
        request.setAttribute(Attribute.GENDERS, genders);
        request.setAttribute(Attribute.COUNTRIES, countries);
        try {
            request.getRequestDispatcher(View.MAIN).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute(Attribute.LANGUAGE);

        int fromAge;
        try {
            fromAge = Integer.parseInt(request.getParameter(Parameter.FROM_AGE));
        } catch (NumberFormatException e) {
            fromAge = Constant.MIN_AGE;
        }
        LocalDate toDate = LocalDate.now().minusYears(fromAge);

        int toAge;
        try {
            toAge = Integer.parseInt(request.getParameter(Parameter.TO_AGE));
        } catch (NumberFormatException e) {
            toAge = Constant.MAX_AGE;
        }

        if (fromAge > toAge) {
            request.setAttribute(Attribute.ERROR, "key.mainPageAgeError");
            doGet(request, response);
        } else {
            LocalDate fromDate = LocalDate.now().minusYears(toAge + 1);

            List<User> users = UserDAO.search(language.getId(), fromDate, toDate);

            try {
                int genderId = Integer.parseInt(request.getParameter(Parameter.GENDER_ID));
                users = users.stream().filter(user -> user.getProfile().getGender().getId() == genderId).collect(Collectors.toList());
            } catch (NumberFormatException ignored) {
            }

            try {
                int countryId = Integer.parseInt(request.getParameter(Parameter.COUNTRY_ID));
                users = users.stream().filter(user -> user.getProfile().getCountry().getId() == countryId).collect(Collectors.toList());
            } catch (NumberFormatException ignored) {
            }

            try {
                int cityId = Integer.parseInt(request.getParameter(Parameter.CITY_ID));
                users = users.stream().filter(user -> user.getProfile().getCity().getId() == cityId).collect(Collectors.toList());
            } catch (NumberFormatException ignored) {
            }

            request.setAttribute(Attribute.USERS, users);
            doGet(request, response);
        }
    }
}
