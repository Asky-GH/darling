package kz.epam.darling.model.dao;

import kz.epam.darling.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<Integer, User> {
    private static final String INSERT_QUERY = "INSERT INTO users(email, password) VALUES(?, ?)";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private RoleDAO roleDAO = new RoleDAO();
    private InfoDAO infoDAO = new InfoDAO();


    public User findByEmail(String email) throws SQLException, ClassNotFoundException, InterruptedException {
        User user = null;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_QUERY)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = retrieveUser(resultSet);
                }
            }
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return user;
    }

    private User retrieveUser(ResultSet resultSet) throws SQLException, InterruptedException, ClassNotFoundException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(roleDAO.findById(resultSet.getInt("role_id")));
        user.setInfo(infoDAO.findByUserId(user.getId()));
        return user;
    }

    @Override
    public void create(User user) throws SQLException, ClassNotFoundException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public List<User> findAll() throws SQLException, ClassNotFoundException, InterruptedException {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = retrieveUser(resultSet);
                users.add(user);
            }
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return users;
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
}
