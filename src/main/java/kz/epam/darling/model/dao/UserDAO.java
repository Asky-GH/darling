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


    public static User findById(int id, int languageId) {
        User user = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = retrieveUser(rs, languageId);
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

    public static User findByEmail(String email, int languageId) {
        User user = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?")) {
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
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = retrieveUser(rs, languageId);
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

    public static List<User> findByConstraints(int genderId, int toYear, int fromYear, int countryId, int cityId,
                                               int languageId) {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM (SELECT * FROM profile WHERE gender_id = ? " +
                                                            "AND country_id = ? AND city_id = ? AND YEAR(birthday) " +
                                                            "BETWEEN ? AND ?) AS p INNER JOIN users ON p.user_id = users.id")) {
            ps.setInt(1, genderId);
            ps.setInt(2, countryId);
            ps.setInt(3, cityId);
            ps.setInt(4, fromYear);
            ps.setInt(5, toYear);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = retrieveUser(rs, languageId);
                    user.setPassword(null);
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

    public static List<User> findByGenderAndCountry(int genderId, int toYear, int fromYear, int countryId, int languageId) {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM (SELECT * FROM profile WHERE gender_id = ? " +
                                                            "AND country_id = ? AND YEAR(birthday) BETWEEN ? AND ?) " +
                                                            "AS p INNER JOIN users ON p.user_id = users.id")) {
            ps.setInt(1, genderId);
            ps.setInt(2, countryId);
            ps.setInt(3, fromYear);
            ps.setInt(4, toYear);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = retrieveUser(rs, languageId);
                    user.setPassword(null);
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

    public static List<User> findByGender(int genderId, int toYear, int fromYear, int languageId) {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM (SELECT * FROM profile WHERE gender_id = ? " +
                                                            "AND YEAR(birthday) BETWEEN ? AND ?) AS p INNER JOIN users " +
                                                            "ON p.user_id = users.id")) {
            ps.setInt(1, genderId);
            ps.setInt(2, fromYear);
            ps.setInt(3, toYear);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = retrieveUser(rs, languageId);
                    user.setPassword(null);
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

    public static List<User> findByCountryAndCity(int toYear, int fromYear, int countryId, int cityId, int languageId) {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM (SELECT * FROM profile WHERE country_id = ? " +
                                                            "AND city_id = ? AND YEAR(birthday) BETWEEN ? AND ?) AS p " +
                                                            "INNER JOIN users ON p.user_id = users.id")) {
            ps.setInt(1, countryId);
            ps.setInt(2, cityId);
            ps.setInt(3, fromYear);
            ps.setInt(4, toYear);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = retrieveUser(rs, languageId);
                    user.setPassword(null);
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

    public static List<User> findByCountry(int toYear, int fromYear, int countryId, int languageId) {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM (SELECT * FROM profile WHERE country_id = ? " +
                                                            "AND YEAR(birthday) BETWEEN ? AND ?) AS p INNER JOIN users " +
                                                            "ON p.user_id = users.id")) {
            ps.setInt(1, countryId);
            ps.setInt(2, fromYear);
            ps.setInt(3, toYear);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = retrieveUser(rs, languageId);
                    user.setPassword(null);
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

    public static List<User> findByAge(int toYear, int fromYear, int languageId) {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM (SELECT * FROM profile WHERE YEAR(birthday) " +
                                                            "BETWEEN ? AND ?) AS p INNER JOIN users ON p.user_id = users.id")) {
            ps.setInt(1, fromYear);
            ps.setInt(2, toYear);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = retrieveUser(rs, languageId);
                    user.setPassword(null);
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

    public static void updateEmail(User user) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("UPDATE users SET email = ? WHERE id = ?")) {
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
        try (PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE id = ?")) {
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
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?")) {
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
            user.setId(rs.getInt("id"));user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(RoleDAO.findById(rs.getInt("role_id")));
            user.setProfile(ProfileDAO.findByUserId(user.getId(), languageId));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return user;
    }
}
