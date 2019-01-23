package kz.epam.darling.model.dao;

import kz.epam.darling.model.Country;
import kz.epam.darling.model.Gender;
import kz.epam.darling.model.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class InfoDAO implements DAO<Integer, Info> {
    private static final Logger LOGGER = LogManager.getLogger(InfoDAO.class.getName());
    private static final String INSERT_QUERY = "INSERT INTO info(first_name, last_name, birthday, gender_id, " +
                                                "country_id, user_id) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_USER_ID_QUERY = "SELECT * FROM info WHERE user_id = ?";
    private Connection connection;
    private GenderDAO genderDAO = new GenderDAO(ConnectionPool.getInstance().takeConnection());
    private CountryDAO countryDAO = new CountryDAO(ConnectionPool.getInstance().takeConnection());


    public InfoDAO(Connection connection) {
        this.connection = connection;
    }

    public Info findByUserId(int id) {
        Info info = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    info = new Info();
                    info.setId(resultSet.getInt("id"));
                    info.setFirstName(resultSet.getString("first_name"));
                    info.setLastName(resultSet.getString("last_name"));
                    int genderId = resultSet.getInt("gender_id");
                    info.setGender(genderDAO.findById(genderId));
                    info.setBirthday(resultSet.getDate("birthday"));
                    int countryId = resultSet.getInt("country_id");
                    info.setCountry(countryDAO.findById(countryId));
                    info.setUserId(id);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return info;
    }

    @Override
    public boolean create(Info info) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, info.getFirstName());
            preparedStatement.setString(2, info.getLastName());
            preparedStatement.setDate(3, info.getBirthday());
            Gender gender = genderDAO.findByName(info.getGender().getName());
            preparedStatement.setInt(4, gender.getId());
            Country country = countryDAO.findByName(info.getCountry().getName());
            preparedStatement.setInt(5, country.getId());
            preparedStatement.setInt(6, info.getUserId());
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
    public List<Info> findAll() {
        return null;
    }

    @Override
    public Info findById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Info entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
