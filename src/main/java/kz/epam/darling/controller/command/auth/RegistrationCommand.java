package kz.epam.darling.controller.command.auth;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.Profile;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.*;
import kz.epam.darling.util.EmailValidator;
import kz.epam.darling.util.PasswordValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.Queue;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("jsp/auth/registration.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            int gender_id = Integer.parseInt(request.getParameter("gender"));
            String birthday = request.getParameter("birthday");
            int country_id = Integer.parseInt(request.getParameter("country_id"));
            int city_id = Integer.parseInt(request.getParameter("city_id"));
            Queue<String> errorMessages = validateInput(email, password, confirmPassword, firstName, lastName, birthday);
            if (!errorMessages.isEmpty()) {
                request.setAttribute("errorMessage", errorMessages.poll());
                request.getRequestDispatcher("jsp/auth/registration.jsp").forward(request, response);
            } else {
                User user = new User();
                user.setEmail(email);
                user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                UserDAO.create(user);
                user = UserDAO.findByEmail(email);
                Profile profile = new Profile();
                profile.setFirstName(firstName);
                profile.setLastName(lastName);
                profile.setGender(GenderDAO.findById(gender_id));
                profile.setBirthday(Date.valueOf(birthday));
                profile.setCountry(CountryDAO.findById(country_id));
                profile.setCity(CityDAO.findById(city_id));
                profile.setUserId(user.getId());
                ProfileDAO.create(profile);
                user.setProfile(profile);
                user.setPassword(null);
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/profile");
            }
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    private Queue<String> validateInput(String email, String password, String confirmPassword, String firstName,
                                        String lastName, String birthday) {
        Queue<String> errorMessages = new LinkedList<>();
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() ||
                lastName.isEmpty() || birthday.isEmpty()) {
            errorMessages.offer("All fields are required!");
        }
        if (!EmailValidator.isValid(email)) {
            errorMessages.offer("Invalid email!");
        }
        if (!PasswordValidator.isValid(password)) {
            errorMessages.offer("Password must be at list 6 characters long! It must contain at least 1 lowercase, " +
                    "1 uppercase latin letter, 1 special character, 1 digit! It must not contain whitespace!");
        }
        if (!password.equals(confirmPassword)) {
            errorMessages.offer("Passwords do not match!");
        }
        User user = UserDAO.findByEmail(email);
        if (user != null) {
            errorMessages.offer("Email already exists!");
        }
        return  errorMessages;
    }
}
