package kz.epam.darling.command;

import kz.epam.darling.constant.*;
import kz.epam.darling.model.*;
import kz.epam.darling.dao.*;
import kz.epam.darling.util.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

public class ProfileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
        List<Country> countries = CountryDAO.findAll(language.getId());
        request.setAttribute(Attribute.COUNTRIES, countries);
        try {
            request.getRequestDispatcher(View.PROFILE).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter(Parameter.ACTION);
        switch (action) {
            case "change-avatar":
                changeAvatar(request, response);
                break;

            case "change-email":
                changeEmail(request, response);
                break;

            case "change-password":
                changePassword(request, response);
                break;

            case "change-location":
                changeLocation(request, response);
                break;
        }
    }

    private void changeAvatar(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(Attribute.PRINCIPAL);
        try {
            Part part = request.getPart(Parameter.IMAGE);
            if (part.getSize() <= 0) {
                request.setAttribute(Attribute.AVATAR_ERROR, "key.profilePageEmptyAvatarError");
            } else {
                if (part.getSize() > Constant.MAX_FILE_SIZE) {
                    request.setAttribute(Attribute.AVATAR_ERROR, "key.profilePageAvatarSizeError");
                } else if (!part.getContentType().contains("image/")) {
                    request.setAttribute(Attribute.AVATAR_ERROR, "key.profilePageAvatarExtensionError");
                } else {
                    Image image = user.getProfile().getImage();
                    image.setUrl(request.getContextPath() + "/image?id=" + image.getId());
                    ImageDAO.update(image, part);
                    user.getProfile().setImage(image);
                    response.sendRedirect(request.getContextPath() + Route.PROFILE);
                    return;
                }
            }
            doGet(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error(e);
        }
    }

    private void changeEmail(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(Parameter.EMAIL);
        try {
            if (email.isEmpty()) {
                request.setAttribute(Attribute.EMAIL_ERROR, "key.profilePageEmptyEmailError");
            } else {
                if (email.length() > Constant.EMAIL_MAX_LENGTH) {
                    request.setAttribute(Attribute.EMAIL_ERROR, "key.profilePageTooLongEmailError");
                } else if (!EmailValidator.isValid(email)) {
                    request.setAttribute(Attribute.EMAIL_ERROR, "key.profilePageInvalidEmailError");
                } else if (UserDAO.emailExists(email)) {
                    request.setAttribute(Attribute.EMAIL_ERROR, "key.profilePageEmailExistsError");
                } else {
                    User user = (User) request.getSession().getAttribute(Attribute.PRINCIPAL);
                    user.setEmail(email);
                    UserDAO.updateEmail(user);
                    response.sendRedirect(request.getContextPath() + Route.PROFILE);
                    return;
                }
            }
            doGet(request, response);
        } catch(IOException e){
            LOGGER.error(e);
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter(Parameter.PASSWORD);
        String confirmPassword = request.getParameter(Parameter.CONFIRM_PASSWORD);
        try {
            if (password.isEmpty() || confirmPassword.isEmpty()) {
                request.setAttribute(Attribute.PASSWORD_ERROR, "key.profilePageEmptyPasswordError");
            } else {
                if (password.length() > Constant.PASSWORD_MAX_LENGTH) {
                    request.setAttribute(Attribute.PASSWORD_ERROR, "key.profilePageTooLongPasswordError");
                } else if (!password.equals(confirmPassword)) {
                    request.setAttribute(Attribute.PASSWORD_ERROR, "key.profilePagePasswordError");
                } else {
                    User user = (User) request.getSession().getAttribute(Attribute.PRINCIPAL);
                    user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                    UserDAO.updatePassword(user);
                    response.sendRedirect(request.getContextPath() + Route.PROFILE);
                    return;
                }
            }
            doGet(request, response);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private void changeLocation(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
        try {
            int countryId = Integer.parseInt(request.getParameter(Parameter.COUNTRY_ID));
            int cityId = Integer.parseInt(request.getParameter(Parameter.CITY_ID));
            User user = (User) request.getSession().getAttribute(Attribute.PRINCIPAL);
            Profile profile = user.getProfile();
            profile.setCountry(CountryDAO.findById(countryId, language.getId()));
            profile.setCity(CityDAO.findById(cityId, language.getId()));
            ProfileDAO.update(profile);
            response.sendRedirect(request.getContextPath() + Route.PROFILE);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (NumberFormatException e) {
            request.setAttribute(Attribute.LOCATION_ERROR, "key.profilePageLocationError");
            doGet(request, response);
        }
    }
}
