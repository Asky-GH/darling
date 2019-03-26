package kz.epam.darling.command.auth;

import kz.epam.darling.command.Command;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.User;
import kz.epam.darling.dao.UserDAO;
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
            request.getRequestDispatcher("jsp/auth/login.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        String referer = request.getParameter("referer");
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (!email.isEmpty() && !password.isEmpty()) {
                User user = UserDAO.findByEmail(email, language.getId());
                if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                    user.setPassword(null);
                    request.getSession(false).setAttribute("principal", user);
                    if (!referer.isEmpty()) {
                        response.sendRedirect(referer);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/main");
                    }
                    return;
                }
            }
            request.setAttribute("referer", referer);
            request.setAttribute("error", "key.loginPageLoginError");
            doGet(request, response);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
