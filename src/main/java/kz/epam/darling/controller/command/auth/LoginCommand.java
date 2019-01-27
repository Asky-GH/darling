package kz.epam.darling.controller.command.auth;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("jsp/auth/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (!email.isEmpty() && !password.isEmpty()) {
            User user = UserDAO.findByEmail(email);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                user.setPassword(null);
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/main");
                return;
            }
        }
        request.setAttribute("errorMessage", "Invalid email or password!");
        request.getRequestDispatcher("jsp/auth/login.jsp").forward(request, response);
    }
}
