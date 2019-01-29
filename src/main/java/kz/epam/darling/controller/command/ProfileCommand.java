package kz.epam.darling.controller.command;

import kz.epam.darling.model.Profile;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.*;
import kz.epam.darling.util.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

public class ProfileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        int country_id = Integer.parseInt(request.getParameter("country_id"));
        int city_id = Integer.parseInt(request.getParameter("city_id"));
        try {
            if (!EmailValidator.isValid(email)) {
                request.setAttribute("errorMessage", "Invalid email!");
                request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
            } else {
                User user = (User) request.getSession(false).getAttribute("user");
                user.setEmail(email);
                UserDAO.update(user);
                Profile profile = user.getProfile();
                profile.setCountry(CountryDAO.findById(country_id));
                profile.setCity(CityDAO.findById(city_id));
                ProfileDAO.update(profile);
                response.sendRedirect(request.getContextPath() + "/profile");
            }
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }
}
