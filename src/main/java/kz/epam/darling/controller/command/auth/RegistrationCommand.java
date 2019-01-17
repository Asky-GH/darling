package kz.epam.darling.controller.command.auth;

import kz.epam.darling.model.dao.ConnectionPool;
import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO remove hardcoded values
public class RegistrationCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getMethod().equals("POST")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            if (!email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                UserDAO userDAO = new UserDAO(ConnectionPool.getInstance().takeConnection());
                // TODO email validation
                User user = userDAO.findByEmail(email);
                if (user != null) {
                    request.setAttribute("errorMessage", "Email already exists!");
                }
                if (password.equals(confirmPassword)) {
                    user = new User();
                    user.setEmail(email);
                    // TODO password validation
                    user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                    userDAO.create(user);
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("/");
                    return;
                } else {
                    request.setAttribute("errorMessage", "Passwords do not match!");
                }
            } else {
                request.setAttribute("errorMessage", "All fields are required!");
            }
        }
        request.getRequestDispatcher("jsp/auth/registration.jsp").forward(request, response);
    }
}
