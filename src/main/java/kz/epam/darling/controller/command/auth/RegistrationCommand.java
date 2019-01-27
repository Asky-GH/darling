package kz.epam.darling.controller.command.auth;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;
import kz.epam.darling.util.EmailValidator;
import kz.epam.darling.util.PasswordValidator;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class RegistrationCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("jsp/auth/registration.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        Queue<String> errorMessages = new LinkedList<>();
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorMessages.offer("All fields are required!");
        }
        if (!EmailValidator.isValid(email)) {
            errorMessages.offer("Invalid email!");
        }
        if (!PasswordValidator.isValid(password)) {
            errorMessages.offer("Password must be at list 6 characters long! It must contain at least 1 lowercase, " +
                                "1 uppercase latin letter, 1 special character, 1 digit! It must not contain whitespace!");
        }
        if (!password.equals(confirmPassword)) {
            errorMessages.offer("Passwords do not match!");
        }
        User user = UserDAO.findByEmail(email);
        if (user != null) {
            errorMessages.offer("Email already exists!");
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute("errorMessage", errorMessages.poll());
            request.getRequestDispatcher("jsp/auth/registration.jsp").forward(request, response);
        } else {
            user = new User();
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            UserDAO.create(user);
            user = UserDAO.findByEmail(email);
            user.setPassword(null);
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
}
