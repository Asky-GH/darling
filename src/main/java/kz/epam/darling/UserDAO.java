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
    private static final String INSERT_QUERY = "INSERT INTO users(email, password, gender_id, role_id) VALUES (?, ?, 1, 1)";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";


    public UserDAO() throws SQLException, ClassNotFoundException {
    }

    @Override
    public boolean create(User user) throws InterruptedException, SQLException {
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
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
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public User authenticateUser(String email, String password) throws InterruptedException, SQLException {
        User user = null;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATION_QUERY)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setGender(resultSet.getString("gender"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return user;
    }

    public boolean findByEmail(String email) throws SQLException, InterruptedException {
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_QUERY)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }
}
