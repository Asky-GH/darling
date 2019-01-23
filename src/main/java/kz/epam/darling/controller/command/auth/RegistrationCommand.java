package kz.epam.darling.controller.command.auth;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationCommand implements Command {
    private UserDAO userDAO = new UserDAO();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("jsp/auth/registration.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                        InterruptedException,
                                                                                        SQLException, ClassNotFoundException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if (password.equals(confirmPassword)) {
                User user = userDAO.findByEmail(email);
                if (user != null) {
                    request.setAttribute("errorMessage", "Email already exists!");
                } else {
                    user = new User();
                    user.setEmail(email);
                    user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                    userDAO.create(user);
                    user = userDAO.findByEmail(email);
                    user.setPassword(null);
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/profile");
                    return;
                }
            } else {
                request.setAttribute("errorMessage", "Passwords do not match!");
            }
        } else {
            request.setAttribute("errorMessage", "All fields are required!");
        }
        request.getRequestDispatcher("jsp/auth/registration.jsp").forward(request, response);
    }
}
