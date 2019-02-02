package kz.epam.darling.controller.command;

import kz.epam.darling.model.Image;
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
import javax.servlet.http.Part;
import java.io.IOException;

public class ProfileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class.getName());
    private static final long MAX_FILE_SIZE = 1_048_576;


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
                request.setAttribute("avatarErrorMessage", "Choose a file!");
                request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
            } else {
                if (part.getSize() > MAX_FILE_SIZE) {
                    request.setAttribute("avatarErrorMessage", "Maximum file size is 1Mb!");
                    request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
                } else {
                    Image image = user.getProfile().getImage();
                    image.setUrl(request.getContextPath() + "/image?id=" + image.getId());
                    ImageDAO.update(image, part);
                    user.getProfile().setImage(image);
                    response.sendRedirect(request.getContextPath() + "/profile");
                }
            }
        } catch (IOException | ServletException e) {
            LOGGER.error(e);
        }
    }

    private void changeEmail(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        try {
            if (email.isEmpty()) {
                request.setAttribute("emailErrorMessage", "Enter new email!");
            } else {
                if (!EmailValidator.isValid(email)) {
                    request.setAttribute("emailErrorMessage", "Invalid email!");
                } else {
                    User user = (User) request.getSession(false).getAttribute("principal");
                    user.setEmail(email);
                    UserDAO.updateEmail(user);
                    response.sendRedirect(request.getContextPath() + "/profile");
                    return;
                }
            }
            request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
        } catch(ServletException | IOException e){
            LOGGER.error(e);
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        try {
            if (password.isEmpty() || confirmPassword.isEmpty()) {
                request.setAttribute("passwordErrorMessage", "All fields are required!");
            } else {
                if (!PasswordValidator.isValid(password)) {
                    request.setAttribute("passwordErrorMessage", "Password must be at list 6 characters long! It must " +
                                                                "contain at least 1 lowercase, 1 uppercase latin letter, " +
                                                                "1 special character, 1 digit! It must not contain whitespace!");
                } else {
                    if (!password.equals(confirmPassword)) {
                        request.setAttribute("passwordErrorMessage", "Passwords do not match!");
                    } else {
                        User user = (User) request.getSession(false).getAttribute("principal");
                        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                        UserDAO.updatePassword(user);
                        user.setPassword(null);
                        response.sendRedirect(request.getContextPath() + "/profile");
                        return;
                    }
                }
            }
            request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    private void changeLocation(HttpServletRequest request, HttpServletResponse response) {
        int country_id = Integer.parseInt(request.getParameter("country_id"));
        int city_id = Integer.parseInt(request.getParameter("city_id"));
        User user = (User) request.getSession(false).getAttribute("principal");
        Profile profile = user.getProfile();
        profile.setCountry(CountryDAO.findById(country_id));
        profile.setCity(CityDAO.findById(city_id));
        ProfileDAO.update(profile);
        try {
            response.sendRedirect(request.getContextPath() + "/profile");
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
