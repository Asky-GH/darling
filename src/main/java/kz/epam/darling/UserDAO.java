package kz.epam.darling;

import java.sql.*;
import java.util.ResourceBundle;

public class UserDAO {
    private final static String AUTHENTICATION_QUERY = "SELECT u.id, u.email, g.gender, r.role FROM users u, " +
                                                        "genders g, roles r WHERE email = ? AND password = ? " +
                                                        "AND u.gender_id = g.id AND u.role_id = r.id";


    public User authenticateUser(String email, String password) {
        User user = null;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String dbUrl = resourceBundle.getString("db.url");
        String dbUser = resourceBundle.getString("db.user");
        String dbPassword = resourceBundle.getString("db.password");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATION_QUERY)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setGender(resultSet.getString("gender"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
