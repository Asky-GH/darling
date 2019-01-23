package kz.epam.darling.model.dao;

import kz.epam.darling.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleDAO implements DAO<Integer, Role> {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM roles WHERE id = ?";

    @Override
    public void create(Role entity) throws SQLException, InterruptedException, ClassNotFoundException {

    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role findById(Integer id) throws SQLException, ClassNotFoundException, InterruptedException {
        Role role = null;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    role = new Role();
                    role.setId(resultSet.getInt("id"));
                    role.setName(resultSet.getString("name"));
                }
            }
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return role;
    }

    @Override
    public boolean update(Role entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
