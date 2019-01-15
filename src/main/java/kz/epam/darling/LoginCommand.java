package kz.epam.darling;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        boolean error = false;
        if (request.getMethod().equals("POST")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (email != null && password != null) {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.authenticateUser(email, password);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("/");
                    return;
                }
                error = true;
            } else {
                error = true;
            }
        }
        if (error) {
            request.setAttribute("errorMessage", "Invalid email or password!");
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
