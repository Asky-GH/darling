package kz.epam.darling.dao;

import kz.epam.darling.model.Gender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenderDAO {
    private static final Logger LOGGER = LogManager.getLogger(GenderDAO.class.getName());


    public static Gender findById(int id, int languageId) {
        Gender gender = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM genders WHERE id = ? AND language_id = ?")) {
            ps.setInt(1, id);
            ps.setInt(2, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gender = retrieveGender(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return gender;
    }

    public static Gender findByType(String type) {
        Gender gender = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM genders WHERE type = ?")) {
            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gender = retrieveGender(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return gender;
    }

    public static List<Gender> findAll(int languageId) {
        List<Gender> genders = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM genders WHERE language_id = ?")) {
            ps.setInt(1, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Gender gender = retrieveGender(rs);
                    genders.add(gender);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return genders;
    }

    public static List<Gender> findAll() {
        List<Gender> genders = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM genders");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Gender gender = retrieveGender(rs);
                genders.add(gender);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return genders;
    }

    public static void create(Gender gender) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO genders(id, language_id, type) VALUES (?, ?, ?)")) {
            ps.setInt(1, gender.getId());
            ps.setInt(2, gender.getLanguage().getId());
            ps.setString(3, gender.getType());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void update(Gender gender) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("UPDATE genders SET type = ? WHERE id = ? AND language_id = ?")) {
            ps.setString(1, gender.getType());
            ps.setInt(2, gender.getId());
            ps.setInt(3, gender.getLanguage().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static List<Integer> findIds() {
        List<Integer> ids = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT DISTINCT id FROM genders");
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return ids;
    }

    private static Gender retrieveGender(ResultSet rs) {
        Gender gender = new Gender();
        try {
            gender.setId(rs.getInt("id"));
            gender.setLanguage(LanguageDAO.findById(rs.getInt("language_id")));
            gender.setType(rs.getString("type"));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return gender;
    }
}