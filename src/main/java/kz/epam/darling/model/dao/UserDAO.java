package kz.epam.darling.model.dao;

import kz.epam.darling.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements DAO<Integer, User> {
    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class.getName());
    private static final String INSERT_QUERY = "INSERT INTO users(email, password, gender_id, role_id) " +
                                                "VALUES (?, ?, 1, 1)";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT u.id, u.email, u.password, g.gender, r.role " +
                                                        "FROM users u, genders g, roles r WHERE email = ? " +
                                                        "AND u.gender_id = g.id AND u.role_id = r.id";
    private Connection connection;


    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
            return false;
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
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
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public User findByEmail(String email) {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_QUERY)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setGender(resultSet.getString("gender"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return user;
    }
}
