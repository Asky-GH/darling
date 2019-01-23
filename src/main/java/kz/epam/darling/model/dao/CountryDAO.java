package kz.epam.darling.model.dao;

import kz.epam.darling.model.Country;
import kz.epam.darling.model.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CountryDAO implements DAO<Integer, Country> {
    private static final Logger LOGGER = LogManager.getLogger(CountryDAO.class.getName());
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM countries WHERE name = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM countries WHERE id = ?";
    private Connection connection;


    public CountryDAO(Connection connection) {
        this.connection = connection;
    }

    public Country findByName(String name) {
        Country country = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            country = getCountry(preparedStatement);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return country;
    }

    private Country getCountry(PreparedStatement preparedStatement) throws SQLException {
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
    public boolean create(Country entity) {
        return false;
    }

    @Override
    public List<Country> findAll() {
        return null;
    }

    @Override
    public Country findById(Integer id) {
        Country country = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            country = getCountry(preparedStatement);
        } catch (SQLException e) {
            LOGGER.error(e);
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
