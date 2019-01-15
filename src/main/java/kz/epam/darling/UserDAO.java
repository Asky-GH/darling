package kz.epam.darling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends DAO<Integer, User> {
    private static final String AUTHENTICATION_QUERY = "SELECT u.id, u.email, g.gender, r.role FROM users u, " +
                                                        "genders g, roles r WHERE email = ? AND password = ? " +
                                                        "AND u.gender_id = g.id AND u.role_id = r.id";


    public UserDAO() throws SQLException {
    }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }


    public User authenticateUser(String email, String password) throws InterruptedException, SQLException {
        User user = null;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATION_QUERY)) {
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
        connectionPool.releaseConnection(connection);
        return user;
    }
}
