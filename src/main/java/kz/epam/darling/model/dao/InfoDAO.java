package kz.epam.darling.model.dao;

import kz.epam.darling.model.Info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InfoDAO implements DAO<Integer, Info> {
    private static final String INSERT_QUERY = "INSERT INTO info(first_name, last_name, birthday, gender_id, " +
                                                "country_id, user_id) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_USER_ID_QUERY = "SELECT * FROM info WHERE user_id = ?";
    private GenderDAO genderDAO = new GenderDAO();
    private CountryDAO countryDAO = new CountryDAO();


    public Info findByUserId(int id) throws SQLException, ClassNotFoundException, InterruptedException {
        Info info = null;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    info = new Info();
                    info.setId(resultSet.getInt("id"));
                    info.setFirstName(resultSet.getString("first_name"));
                    info.setLastName(resultSet.getString("last_name"));
                    info.setGender(genderDAO.findById(resultSet.getInt("gender_id")));
                    info.setBirthday(resultSet.getDate("birthday"));
                    info.setCountry(countryDAO.findById(resultSet.getInt("country_id")));
                    info.setUserId(id);
                }
            }
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return info;
    }

    @Override
    public void create(Info info) throws SQLException, ClassNotFoundException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, info.getFirstName());
            preparedStatement.setString(2, info.getLastName());
            preparedStatement.setDate(3, info.getBirthday());
            preparedStatement.setInt(4, info.getGender().getId());
            preparedStatement.setInt(5, info.getCountry().getId());
            preparedStatement.setInt(6, info.getUserId());
            preparedStatement.executeUpdate();
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
