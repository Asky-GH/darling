package kz.epam.darling;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        boolean error = false;
        if (request.getMethod().equals("POST")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (email != null && password != null) {
                ResourceBundle rb = ResourceBundle.getBundle("database");
                String dbUrl = rb.getString("db.url");
                String dbUser = rb.getString("db.user");
                String dbPassword = rb.getString("db.password");
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                String query = "SELECT u.id, u.email, g.gender, r.role FROM users u, genders g, roles r WHERE email = ? AND password = ? AND u.gender_id = g.id AND u.role_id = r.id";
                PreparedStatement authenticateUser = connection.prepareStatement(query);
                authenticateUser.setString(1, email);
                authenticateUser.setString(2, password);
                ResultSet resultSet = authenticateUser.executeQuery();
                User user = null;
                while (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setGender(resultSet.getString("gender"));
                    user.setRole(resultSet.getString("role"));
                }
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
