package kz.epam.darling.model.dao;

import kz.epam.darling.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CountryDAO implements DAO<Integer, Country> {
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM countries WHERE name = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM countries WHERE id = ?";


    public Country findByName(String name) throws SQLException, ClassNotFoundException, InterruptedException {
        Country country;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            country = retrieveCountry(preparedStatement);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return country;
    }

    private Country retrieveCountry(PreparedStatement preparedStatement) throws SQLException {
        Country country = null;
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                country = new Country();
                country.setId(resultSet.getInt("id"));
                country.setName(resultSet.getString("name"));
            }
        }
        return country;
    }

    @Override
    public void create(Country entity) {
    }

    @Override
    public List<Country> findAll() {
        return null;
    }

    @Override
    public Country findById(Integer id) throws SQLException, ClassNotFoundException, InterruptedException {
        Country country;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            country = retrieveCountry(preparedStatement);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return country;
    }

    @Override
    public boolean update(Country entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
