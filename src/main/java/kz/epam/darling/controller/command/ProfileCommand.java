package kz.epam.darling.controller.command;

import kz.epam.darling.model.*;
import kz.epam.darling.model.dao.*;
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
    private static final long MAX_FILE_SIZE = 1_048_576;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        List<Country> countries = CountryDAO.findAll(language.getId());
        request.setAttribute("countries", countries);
        try {
            request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
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
        User user = (User) request.getSession(false).getAttribute("principal");
        try {
            Part part = request.getPart("image");
            if (part.getSize() <= 0) {
                request.setAttribute("avatarError", "key.profilePageEmptyAvatarError");
            } else {
                if (part.getSize() > MAX_FILE_SIZE) {
                    request.setAttribute("avatarError", "key.profilePageAvatarSizeError");
                } else if (!part.getContentType().contains("image/")) {
                    request.setAttribute("avatarError", "key.profilePageAvatarExtensionError");
                } else {
                    Image image = user.getProfile().getImage();
                    image.setUrl(request.getContextPath() + "/image?id=" + image.getId());
                    ImageDAO.update(image, part);
                    user.getProfile().setImage(image);
                    response.sendRedirect(request.getContextPath() + "/profile");
                    return;
                }
            }
            doGet(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error(e);
        }
    }

    private void changeEmail(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        try {
            if (email.isEmpty()) {
                request.setAttribute("emailError", "key.profilePageEmptyEmailError");
            } else {
                if (email.length() > 100) {
                    request.setAttribute("emailError", "key.profilePageTooLongEmailError");
                } else if (!EmailValidator.isValid(email)) {
                    request.setAttribute("emailError", "key.profilePageInvalidEmailError");
                } else if (UserDAO.emailExists(email)) {
                    request.setAttribute("emailError", "key.profilePageEmailExistsError");
                } else {
                    User user = (User) request.getSession(false).getAttribute("principal");
                    user.setEmail(email);
                    UserDAO.updateEmail(user);
                    response.sendRedirect(request.getContextPath() + "/profile");
                    return;
                }
            }
            doGet(request, response);
        } catch(IOException e){
            LOGGER.error(e);
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        try {
            if (password.isEmpty() || confirmPassword.isEmpty()) {
                request.setAttribute("passwordError", "key.profilePageEmptyPasswordError");
            } else {
                if (password.length() > 100) {
                    request.setAttribute("passwordError", "key.profilePageTooLongPasswordError");
                } else if (!password.equals(confirmPassword)) {
                    request.setAttribute("passwordError", "key.profilePagePasswordError");
                } else {
                    User user = (User) request.getSession(false).getAttribute("principal");
                    user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                    UserDAO.updatePassword(user);
                    user.setPassword(null);
                    response.sendRedirect(request.getContextPath() + "/profile");
                    return;
                }
            }
            doGet(request, response);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private void changeLocation(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        try {
            int countryId = Integer.parseInt(request.getParameter("countryId"));
            int cityId = Integer.parseInt(request.getParameter("cityId"));
            User user = (User) request.getSession(false).getAttribute("principal");
            Profile profile = user.getProfile();
            profile.setCountry(CountryDAO.findById(countryId, language.getId()));
            profile.setCity(CityDAO.findById(cityId, language.getId()));
            ProfileDAO.update(profile);
            response.sendRedirect(request.getContextPath() + "/profile");
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (NumberFormatException e) {
            request.setAttribute("locationError", "key.profilePageLocationError");
            doGet(request, response);
        }
    }
}
