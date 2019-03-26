package kz.epam.darling.command.auth;

import kz.epam.darling.command.Command;
import kz.epam.darling.model.*;
import kz.epam.darling.dao.*;
import kz.epam.darling.util.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

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
        processEmail(request);
        processPassword(request);
        processFirstName(request);
        processLastName(request);
        processBirthday(request);
        processCountry(request);
        processCity(request);
        processGender(request);
        if (request.getAttribute("error") == null) {
            Language language = (Language) request.getAttribute("language");
            User user = new User();
            user.setEmail(request.getParameter("email"));
            user.setPassword(BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt()));
            UserDAO.create(user);
            user = UserDAO.findByEmail(request.getParameter("email"), language.getId());
            Image image = new Image();
            image.setUser_id(user.getId());
            ImageDAO.create(image);
            image = ImageDAO.findByUserId(user.getId());
            Profile profile = new Profile();
            profile.setFirstName(request.getParameter("firstName"));
            profile.setLastName(request.getParameter("lastName"));
            profile.setGender(GenderDAO.findById(Integer.parseInt(request.getParameter("genderId")), language.getId()));
            profile.setBirthday(Date.valueOf(request.getParameter("birthday")));
            profile.setCountry(CountryDAO.findById(Integer.parseInt(request.getParameter("countryId")), language.getId()));
            profile.setCity(CityDAO.findById(Integer.parseInt(request.getParameter("cityId")), language.getId()));
            profile.setUserId(user.getId());
            profile.setImage(image);
            ProfileDAO.create(profile);
            user.setProfile(profile);
            user.setPassword(null);
            request.getSession().setAttribute("principal", user);
            try {
                response.sendRedirect(request.getContextPath() + "/profile");
            } catch (IOException e) {
                LOGGER.error(e);
            }
        } else {
            doGet(request, response);
        }
    }

    private void processEmail(HttpServletRequest request) {
        boolean emailIsValid = true;

        String email = request.getParameter("email");
        if (email.isEmpty()) {
            emailIsValid = false;
            request.setAttribute("email", "key.registrationPageEmptyEmailError");
        } else if (email.length() > 100) {
            emailIsValid = false;
            request.setAttribute("email", "key.registrationPageTooLongEmailError");
        } else if (!EmailValidator.isValid(email)) {
            emailIsValid = false;
            request.setAttribute("email", "key.registrationPageInvalidEmailError");
        } else if (UserDAO.emailExists(email)) {
            emailIsValid = false;
            request.setAttribute("email", "key.registrationPageExistingEmailError");
        }

        if (!emailIsValid) {
            request.setAttribute("error", true);
        } else {
            request.setAttribute("email", email);
        }
    }

    private void processPassword(HttpServletRequest request) {
        boolean passwordIsValid = true;

        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!password.isEmpty() && !confirmPassword.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                passwordIsValid = false;
                request.setAttribute("password", "key.registrationPagePasswordError");
            }
            if (password.length() > 100) {
                passwordIsValid = false;
                request.setAttribute("password", "key.registrationPageTooLongPasswordError");
            }
        } else {
            if (password.isEmpty()) {
                passwordIsValid = false;
                request.setAttribute("password", "key.registrationPageEmptyPasswordError");
            }
            if (confirmPassword.isEmpty()) {
                passwordIsValid = false;
                request.setAttribute("confirmPassword", "key.registrationPageEmptyConfirmPasswordError");
            }
        }

        if (!passwordIsValid) {
            request.setAttribute("error", true);
        }
    }

    private void processFirstName(HttpServletRequest request) {
        boolean firstNameIsValid = true;

        String firstName = request.getParameter("firstName");
        if (firstName.isEmpty()) {
            firstNameIsValid = false;
            request.setAttribute("firstName", "key.registrationPageEmptyFirstNameError");
        } else if (firstName.length() > 50) {
            firstNameIsValid = false;
            request.setAttribute("firstName", "key.registrationPageTooLongFirstNameError");
        }

        if (!firstNameIsValid) {
            request.setAttribute("error", true);
        } else {
            request.setAttribute("firstName", firstName);
        }
    }

    private void processLastName(HttpServletRequest request) {
        boolean lastNameIsValid = true;

        String lastName = request.getParameter("lastName");
        if (lastName.isEmpty()) {
            lastNameIsValid = false;
            request.setAttribute("lastName", "key.registrationPageEmptyLastNameError");
        } else if (lastName.length() > 50) {
            lastNameIsValid = false;
            request.setAttribute("lastName", "key.registrationPageTooLongLastNameError");
        }

        if (!lastNameIsValid) {
            request.setAttribute("error", true);
        } else {
            request.setAttribute("lastName", lastName);
        }
    }

    private void processBirthday(HttpServletRequest request) {
        boolean birthdayIsValid = true;

        String birthday = request.getParameter("birthday");
        if (birthday.isEmpty()) {
            birthdayIsValid = false;
            request.setAttribute("birthday", "key.registrationPageEmptyBirthdayError");
        }

        if (!birthdayIsValid) {
            request.setAttribute("error", true);
        } else {
            request.setAttribute("birthday", birthday);
        }
    }

    private void processCountry(HttpServletRequest request) {
        boolean countryIsValid = true;

        String countryId = request.getParameter("countryId");
        try {
            Integer.parseInt(countryId);
        } catch (NumberFormatException e) {
            countryIsValid = false;
            request.setAttribute("countryError", "key.registrationPageEmptyCountryError");
        }

        if (!countryIsValid) {
            request.setAttribute("error", true);
        }
    }

    private void processCity(HttpServletRequest request) {
        boolean cityIdIsValid = true;

        String cityId = request.getParameter("cityId");
        try {
            Integer.parseInt(cityId);
        } catch (NumberFormatException e) {
            cityIdIsValid = false;
            request.setAttribute("cityError", "key.registrationPageEmptyCityError");
        }

        if (!cityIdIsValid) {
            request.setAttribute("error", true);
        }
    }

    private void processGender(HttpServletRequest request) {
        boolean genderIsValid = true;

        String genderId = request.getParameter("genderId");
        if (genderId == null) {
            genderIsValid = false;
            request.setAttribute("genderId", "key.registrationEmptyGenderError");
        }

        if (!genderIsValid) {
            request.setAttribute("error", true);
        } else {
            request.setAttribute("genderId", genderId);
        }
    }
}
