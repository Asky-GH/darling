package kz.epam.darling.command.auth;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.*;
import kz.epam.darling.dao.*;
import kz.epam.darling.model.*;
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
        Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
        List<Gender> genders = GenderDAO.findAll(language.getId());
        List<Country> countries = CountryDAO.findAll(language.getId());
        request.setAttribute(Attribute.GENDERS, genders);
        request.setAttribute(Attribute.COUNTRIES, countries);
        try {
            request.getRequestDispatcher(View.REGISTRATION).forward(request, response);
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
        if (request.getAttribute(Attribute.ERROR) == null) {
            Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
            User user = new User();
            user.setEmail(request.getParameter(Parameter.EMAIL));
            user.setPassword(BCrypt.hashpw(request.getParameter(Parameter.PASSWORD), BCrypt.gensalt()));
            UserDAO.create(user);
            user = UserDAO.findByEmail(request.getParameter(Parameter.EMAIL), language.getId());
            Image image = new Image();
            image.setUser_id(user.getId());
            ImageDAO.create(image);
            image = ImageDAO.findByUserId(user.getId());
            Profile profile = new Profile();
            profile.setFirstName(request.getParameter(Parameter.FIRST_NAME));
            profile.setLastName(request.getParameter(Parameter.LAST_NAME));
            profile.setGender(GenderDAO.findById(Integer.parseInt(request.getParameter(Parameter.GENDER_ID)), language.getId()));
            profile.setBirthday(Date.valueOf(request.getParameter(Parameter.BIRTHDAY)));
            profile.setCountry(CountryDAO.findById(Integer.parseInt(request.getParameter(Parameter.COUNTRY_ID)), language.getId()));
            profile.setCity(CityDAO.findById(Integer.parseInt(request.getParameter(Parameter.CITY_ID)), language.getId()));
            profile.setUserId(user.getId());
            profile.setImage(image);
            ProfileDAO.create(profile);
            user.setProfile(profile);
            request.getSession().setAttribute(Attribute.PRINCIPAL, user);
            try {
                response.sendRedirect(request.getContextPath() + Route.PROFILE);
            } catch (IOException e) {
                LOGGER.error(e);
            }
        } else {
            doGet(request, response);
        }
    }

    private void processEmail(HttpServletRequest request) {
        boolean emailIsValid = true;

        String email = request.getParameter(Parameter.EMAIL);
        if (email.isEmpty()) {
            emailIsValid = false;
            request.setAttribute(Attribute.EMAIL, "key.registrationPageEmptyEmailError");
        } else if (email.length() > Constant.EMAIL_MAX_LENGTH) {
            emailIsValid = false;
            request.setAttribute(Attribute.EMAIL, "key.registrationPageTooLongEmailError");
        } else if (!EmailValidator.isValid(email)) {
            emailIsValid = false;
            request.setAttribute(Attribute.EMAIL, "key.registrationPageInvalidEmailError");
        } else if (UserDAO.emailExists(email)) {
            emailIsValid = false;
            request.setAttribute(Attribute.EMAIL, "key.registrationPageExistingEmailError");
        }

        if (!emailIsValid) {
            request.setAttribute(Attribute.ERROR, true);
        } else {
            request.setAttribute(Attribute.EMAIL, email);
        }
    }

    private void processPassword(HttpServletRequest request) {
        boolean passwordIsValid = true;

        String password = request.getParameter(Parameter.PASSWORD);
        String confirmPassword = request.getParameter(Parameter.CONFIRM_PASSWORD);
        if (!password.isEmpty() && !confirmPassword.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                passwordIsValid = false;
                request.setAttribute(Attribute.PASSWORD, "key.registrationPagePasswordError");
            }
            if (password.length() > Constant.PASSWORD_MAX_LENGTH) {
                passwordIsValid = false;
                request.setAttribute(Attribute.PASSWORD, "key.registrationPageTooLongPasswordError");
            }
        } else {
            if (password.isEmpty()) {
                passwordIsValid = false;
                request.setAttribute(Attribute.PASSWORD, "key.registrationPageEmptyPasswordError");
            }
            if (confirmPassword.isEmpty()) {
                passwordIsValid = false;
                request.setAttribute(Attribute.CONFIRM_PASSWORD, "key.registrationPageEmptyConfirmPasswordError");
            }
        }

        if (!passwordIsValid) {
            request.setAttribute(Attribute.ERROR, true);
        }
    }

    private void processFirstName(HttpServletRequest request) {
        boolean firstNameIsValid = true;

        String firstName = request.getParameter(Parameter.FIRST_NAME);
        if (firstName.isEmpty()) {
            firstNameIsValid = false;
            request.setAttribute(Attribute.FIRST_NAME, "key.registrationPageEmptyFirstNameError");
        } else if (firstName.length() > Constant.FIRST_NAME_MAX_LENGTH) {
            firstNameIsValid = false;
            request.setAttribute(Attribute.FIRST_NAME, "key.registrationPageTooLongFirstNameError");
        }

        if (!firstNameIsValid) {
            request.setAttribute(Attribute.ERROR, true);
        } else {
            request.setAttribute(Attribute.FIRST_NAME, firstName);
        }
    }

    private void processLastName(HttpServletRequest request) {
        boolean lastNameIsValid = true;

        String lastName = request.getParameter(Parameter.LAST_NAME);
        if (lastName.isEmpty()) {
            lastNameIsValid = false;
            request.setAttribute(Attribute.LAST_NAME, "key.registrationPageEmptyLastNameError");
        } else if (lastName.length() > Constant.LAST_NAME_MAX_LENGTH) {
            lastNameIsValid = false;
            request.setAttribute(Attribute.LAST_NAME, "key.registrationPageTooLongLastNameError");
        }

        if (!lastNameIsValid) {
            request.setAttribute(Attribute.ERROR, true);
        } else {
            request.setAttribute(Attribute.LAST_NAME, lastName);
        }
    }

    private void processBirthday(HttpServletRequest request) {
        boolean birthdayIsValid = true;

        String birthday = request.getParameter(Parameter.BIRTHDAY);
        if (birthday.isEmpty()) {
            birthdayIsValid = false;
            request.setAttribute(Attribute.BIRTHDAY, "key.registrationPageEmptyBirthdayError");
        }

        if (!birthdayIsValid) {
            request.setAttribute(Attribute.ERROR, true);
        } else {
            request.setAttribute(Attribute.BIRTHDAY, birthday);
        }
    }

    private void processCountry(HttpServletRequest request) {
        boolean countryIsValid = true;

        String countryId = request.getParameter(Parameter.COUNTRY_ID);
        try {
            Integer.parseInt(countryId);
        } catch (NumberFormatException e) {
            countryIsValid = false;
            request.setAttribute(Attribute.COUNTRY_ERROR, "key.registrationPageEmptyCountryError");
        }

        if (!countryIsValid) {
            request.setAttribute(Attribute.ERROR, true);
        }
    }

    private void processCity(HttpServletRequest request) {
        boolean cityIdIsValid = true;

        String cityId = request.getParameter(Parameter.CITY_ID);
        try {
            Integer.parseInt(cityId);
        } catch (NumberFormatException e) {
            cityIdIsValid = false;
            request.setAttribute(Attribute.CITY_ERROR, "key.registrationPageEmptyCityError");
        }

        if (!cityIdIsValid) {
            request.setAttribute(Attribute.ERROR, true);
        }
    }

    private void processGender(HttpServletRequest request) {
        boolean genderIsValid = true;

        String genderId = request.getParameter(Parameter.GENDER_ID);
        if (genderId == null) {
            genderIsValid = false;
            request.setAttribute(Attribute.GENDER_ID, "key.registrationEmptyGenderError");
        }

        if (!genderIsValid) {
            request.setAttribute(Attribute.ERROR, true);
        } else {
            request.setAttribute(Attribute.GENDER_ID, genderId);
        }
    }
}
