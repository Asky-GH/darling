package kz.epam.darling.dao;

import kz.epam.darling.constant.Column;
import kz.epam.darling.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class.getName());
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String SEARCH_QUERY = "SELECT * FROM (SELECT * FROM profile WHERE birthday BETWEEN ? AND ?) " +
                                                "AS p JOIN users ON p.user_id=users.id";
    private static final String CREATE_QUERY = "INSERT INTO users(email, password) VALUES(?,?)";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
    private static final String UPDATE_QUERY = "UPDATE users SET role_id=? WHERE id=?";
    private static final String UPDATE_EMAIL_QUERY = "UPDATE users SET email=? WHERE id=?";
    private static final String UPDATE_PASSWORD_QUERY = "UPDATE users SET password=? WHERE id=?";
    private static final String EMAIL_EXISTS_QUERY = "SELECT * FROM users WHERE email=?";

    public static User findById(int id, int languageId) {
        User user = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = retrieveUser(rs, languageId);
                }
            }

        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return user;
    }

    public static User findByEmail(String email, int languageId) {
        User user = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_EMAIL_QUERY)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = retrieveUser(rs, languageId);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return user;
    }

    public static List<User> findAll(int languageId) {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_ALL_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = retrieveUser(rs, languageId);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return users;
    }

    public static List<User> search(int languageId, LocalDate fromDate, LocalDate toDate) {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(SEARCH_QUERY)) {
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = retrieveUser(rs, languageId);
                    users.add(user);
                }
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
        try (PreparedStatement ps = con.prepareStatement(CREATE_QUERY)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void delete(int id) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(DELETE_QUERY)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void update(User user) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {
            ps.setInt(1, user.getRole().getId());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void updateEmail(User user) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(UPDATE_EMAIL_QUERY)) {
            ps.setString(1, user.getEmail());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void updatePassword(User user) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(UPDATE_PASSWORD_QUERY)) {
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static boolean emailExists(String email) {
        boolean emailExists = false;

        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(EMAIL_EXISTS_QUERY)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emailExists = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }

        return emailExists;
    }

    private static User retrieveUser(ResultSet rs, int languageId) {
        User user = new User();
        try {
            user.setId(rs.getInt(Column.ID));user.setEmail(rs.getString(Column.EMAIL));
            user.setPassword(rs.getString(Column.PASSWORD));
            user.setRole(RoleDAO.findById(rs.getInt(Column.ROLE_ID)));
            user.setProfile(ProfileDAO.findByUserId(user.getId(), languageId));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return user;
    }
}
