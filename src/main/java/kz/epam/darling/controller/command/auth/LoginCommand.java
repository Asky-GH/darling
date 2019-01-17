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
public class LoginCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getMethod().equals("POST")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (!email.isEmpty() && !password.isEmpty()) {
                UserDAO userDAO = new UserDAO(ConnectionPool.getInstance().takeConnection());
                User user = userDAO.findByEmail(email);
                if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                    user.setPassword(null);
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("/");
                    return;
                }
            }
            request.setAttribute("errorMessage", "Invalid email or password!");
        }
        request.getRequestDispatcher("jsp/auth/login.jsp").forward(request, response);
    }
}
