package kz.epam.darling.model.dao;

import kz.epam.darling.model.Gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GenderDAO implements DAO<Integer, Gender> {
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM genders WHERE name = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM genders WHERE id = ?";


    public Gender findByName(String name) throws SQLException, ClassNotFoundException, InterruptedException {
        Gender gender;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            gender = retrieveGender(preparedStatement);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return gender;
    }

    private Gender retrieveGender(PreparedStatement preparedStatement) throws SQLException {
        Gender gender = null;
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                gender = new Gender();
                gender.setId(resultSet.getInt("id"));
                gender.setName(resultSet.getString("name"));
            }
        }
        return gender;
    }

    @Override
    public void create(Gender entity) {
    }

    @Override
    public List<Gender> findAll() {
        return null;
    }

    @Override
    public Gender findById(Integer id) throws SQLException, ClassNotFoundException, InterruptedException {
        Gender gender;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            gender = retrieveGender(preparedStatement);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return gender;
    }

    @Override
    public boolean update(Gender entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
