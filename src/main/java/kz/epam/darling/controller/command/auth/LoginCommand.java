package kz.epam.darling.controller.command.auth;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;
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
        String from = request.getParameter("from");
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (!email.isEmpty() && !password.isEmpty()) {
                User user = UserDAO.findByEmail(email);
                if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                    user.setPassword(null);
                    request.getSession().setAttribute("principal", user);
                    response.sendRedirect(from);
                    return;
                }
            }
            request.setAttribute("from", from);
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.getRequestDispatcher("jsp/auth/login.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }
}
