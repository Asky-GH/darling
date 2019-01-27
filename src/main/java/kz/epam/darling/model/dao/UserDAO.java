package kz.epam.darling.model.dao;

import kz.epam.darling.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class.getName());


    public static User findById(Integer id) {
        User user = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = retrieveUser(rs);
                    user.setPassword(null);
                }
            }

        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return user;
    }

    public static User findByEmail(String email) {
        User user = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = retrieveUser(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return user;
    }

    public static List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = retrieveUser(rs);
                user.setPassword(null);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return users;
    }

    public static void create(User user) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO users(email, password) VALUES (?, ?)")) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    private static User retrieveUser(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(RoleDAO.findById(rs.getInt("role_id")));
            user.setInfo(InfoDAO.findByUserId(user.getId()));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return user;
    }
}
