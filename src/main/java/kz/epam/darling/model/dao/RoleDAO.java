package kz.epam.darling.model.dao;

import kz.epam.darling.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private static final Logger LOGGER = LogManager.getLogger(RoleDAO.class.getName());


    public static Role findById(Integer id) {
        Role role = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM roles WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    role = retrieveRole(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return role;
    }

    public static List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM roles");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Role role = retrieveRole(rs);
                roles.add(role);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return roles;
    }

    private static Role retrieveRole(ResultSet rs) {
        Role role = new Role();
        try {
            role.setId(rs.getInt("id"));
            role.setType(rs.getString("type"));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return role;
    }
}
