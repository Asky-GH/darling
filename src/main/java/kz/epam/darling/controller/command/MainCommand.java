package kz.epam.darling.controller.command;

import kz.epam.darling.model.Country;
import kz.epam.darling.model.Gender;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.User;
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
import java.util.stream.Collectors;

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

        int fromAge;
        try {
            fromAge = Integer.parseInt(request.getParameter("fromAge"));
        } catch (NumberFormatException e) {
            fromAge = 18;
        }
        LocalDate toDate = LocalDate.now().minusYears(fromAge);

        int toAge;
        try {
            toAge = Integer.parseInt(request.getParameter("toAge"));
        } catch (NumberFormatException e) {
            toAge = 100;
        }
        LocalDate fromDate = LocalDate.now().minusYears(toAge + 1);

        List<User> users = UserDAO.search(language.getId(), fromDate, toDate);

        try {
            int genderId = Integer.parseInt(request.getParameter("genderId"));
            users = users.stream().filter(user -> user.getProfile().getGender().getId() == genderId).collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int countryId = Integer.parseInt(request.getParameter("countryId"));
            users = users.stream().filter(user -> user.getProfile().getCountry().getId() == countryId).collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int cityId = Integer.parseInt(request.getParameter("cityId"));
            users = users.stream().filter(user -> user.getProfile().getCity().getId() == cityId).collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        request.setAttribute("users", users);
        doGet(request, response);
    }
}
