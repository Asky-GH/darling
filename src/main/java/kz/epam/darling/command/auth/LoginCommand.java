package kz.epam.darling.command.auth;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.Attribute;
import kz.epam.darling.constant.Parameter;
import kz.epam.darling.constant.Route;
import kz.epam.darling.constant.View;
import kz.epam.darling.dao.UserDAO;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(View.LOGIN).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
        String referer = request.getParameter(Attribute.REFERER);
        try {
            String email = request.getParameter(Parameter.EMAIL);
            String password = request.getParameter(Parameter.PASSWORD);
            if (!email.isEmpty() && !password.isEmpty()) {
                User user = UserDAO.findByEmail(email, language.getId());
                if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                    request.getSession().setAttribute(Attribute.PRINCIPAL, user);
                    if (!referer.isEmpty()) {
                        response.sendRedirect(referer);
                    } else {
                        response.sendRedirect(request.getContextPath() + Route.MAIN);
                    }
                    return;
                }
            }
            request.setAttribute(Attribute.REFERER, referer);
            request.setAttribute(Attribute.ERROR, "key.loginPageLoginError");
            doGet(request, response);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
