package kz.epam.darling.dao;

import kz.epam.darling.model.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {
    private static final Logger LOGGER = LogManager.getLogger(CountryDAO.class.getName());


    public static Country findById(int id, int languageId) {
        Country country = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM countries WHERE id = ? AND language_id = ?")) {
            ps.setInt(1, id);
            ps.setInt(2, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    country = retrieveCountry(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return country;
    }

    public static Country findByName(String name) {
        Country country = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM countries WHERE name = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    country = retrieveCountry(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return country;
    }

    public static List<Country> findAll(int languageId) {
        List<Country> countries = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM countries WHERE language_id = ?")) {
            ps.setInt(1, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Country country = retrieveCountry(rs);
                    countries.add(country);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return countries;
    }

    public static List<Country> findAll() {
        List<Country> countries = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM countries");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Country country = retrieveCountry(rs);
                countries.add(country);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return countries;
    }

    public static List<Integer> findIds() {
        List<Integer> ids = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT DISTINCT id FROM countries");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return ids;
    }

    public static void create(Country country) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO countries(id, language_id, name) VALUES (?, ?, ?)")) {
            ps.setInt(1, country.getId());
            ps.setInt(2, country.getLanguage().getId());
            ps.setString(3, country.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void update(Country country) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("UPDATE countries SET name = ? WHERE id = ? AND language_id = ?")) {
            ps.setString(1, country.getName());
            ps.setInt(2, country.getId());
            ps.setInt(3, country.getLanguage().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    private static Country retrieveCountry(ResultSet rs) {
        Country country = new Country();
        try {
            country.setId(rs.getInt("id"));
            country.setLanguage(LanguageDAO.findById(rs.getInt("language_id")));
            country.setName(rs.getString("name"));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return country;
    }
}
