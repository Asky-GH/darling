package kz.epam.darling.controller.command.auth;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.*;
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
import java.util.List;
import java.util.Queue;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        List<Gender> genders = GenderDAO.findAll(language.getId());
        List<Country> countries = CountryDAO.findAll(language.getId());
        request.setAttribute("genders", genders);
        request.setAttribute("countries", countries);
        try {
            request.getRequestDispatcher("jsp/auth/registration.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            int genderId = Integer.parseInt(request.getParameter("genderId"));
            String birthday = request.getParameter("birthday");
            int countryId = Integer.parseInt(request.getParameter("countryId"));
            int cityId = Integer.parseInt(request.getParameter("cityId"));
            Queue<String> errorMessages = validateInput(email, password, confirmPassword, firstName, lastName, birthday,
                                                        language.getId());
            if (!errorMessages.isEmpty()) {
                request.setAttribute("errorMessage", errorMessages.poll());
                doGet(request, response);
            } else {
                User user = new User();
                user.setEmail(email);
                user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                UserDAO.create(user);
                user = UserDAO.findByEmail(email, language.getId());
                Image image = new Image();
                image.setUser_id(user.getId());
                ImageDAO.create(image);
                image = ImageDAO.findByUserId(user.getId());
                Profile profile = new Profile();
                profile.setFirstName(firstName);
                profile.setLastName(lastName);
                profile.setGender(GenderDAO.findById(genderId, language.getId()));
                profile.setBirthday(Date.valueOf(birthday));
                profile.setCountry(CountryDAO.findById(countryId, language.getId()));
                profile.setCity(CityDAO.findById(cityId, language.getId()));
                profile.setUserId(user.getId());
                profile.setImage(image);
                ProfileDAO.create(profile);
                user.setProfile(profile);
                user.setPassword(null);
                request.getSession().setAttribute("principal", user);
                response.sendRedirect(request.getContextPath() + "/profile");
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private Queue<String> validateInput(String email, String password, String confirmPassword, String firstName,
                                        String lastName, String birthday, int languageId) {
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
        User user = UserDAO.findByEmail(email, languageId);
        if (user != null) {
            errorMessages.offer("Email already exists!");
        }
        return  errorMessages;
    }
}
