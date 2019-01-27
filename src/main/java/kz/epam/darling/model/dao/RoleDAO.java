package kz.epam.darling.model.dao;

import kz.epam.darling.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class RoleDAO {
    private static final Logger LOGGER = LogManager.getLogger(RoleDAO.class.getName());


    static Role findById(Integer id) {
        Role role = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM roles WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return role;
    }
}
