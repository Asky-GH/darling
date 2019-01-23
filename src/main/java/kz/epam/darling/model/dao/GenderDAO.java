package kz.epam.darling.model.dao;

import kz.epam.darling.model.Gender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GenderDAO implements DAO<Integer, Gender> {
    private static final Logger LOGGER = LogManager.getLogger(GenderDAO.class.getName());
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM genders WHERE name = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM genders WHERE id = ?";
    private Connection connection;


    public GenderDAO(Connection connection) {
        this.connection = connection;
    }

    public Gender findByName(String name) {
        Gender gender = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            gender = getGender(preparedStatement);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return gender;
    }

    private Gender getGender(PreparedStatement preparedStatement) throws SQLException {
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
    public boolean create(Gender entity) {
        return false;
    }

    @Override
    public List<Gender> findAll() {
        return null;
    }

    @Override
    public Gender findById(Integer id) {
        Gender gender = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            gender = getGender(preparedStatement);
        } catch (SQLException e) {
            LOGGER.error(e);
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
