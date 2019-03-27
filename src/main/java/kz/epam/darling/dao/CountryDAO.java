package kz.epam.darling.dao;

import kz.epam.darling.constant.Column;
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
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM countries WHERE id=? AND language_id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM countries WHERE language_id=?";
    private static final String FIND_QUERY = "SELECT * FROM countries";
    private static final String FIND_IDS_QUERY = "SELECT DISTINCT id FROM countries";
    private static final String CREATE_QUERY = "INSERT INTO countries(id, language_id, name) VALUES(?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE countries SET name=? WHERE id=? AND language_id=?";

    public static Country findById(int id, int languageId) {
        Country country = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_ID_QUERY)) {
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

    public static List<Country> findAll(int languageId) {
        List<Country> countries = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_ALL_QUERY)) {
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

    public static List<Country> find() {
        List<Country> countries = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_QUERY);
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
        try (PreparedStatement ps = con.prepareStatement(FIND_IDS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt(Column.ID));
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
        try (PreparedStatement ps = con.prepareStatement(CREATE_QUERY)) {
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
        try (PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {
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
            country.setId(rs.getInt(Column.ID));
            country.setLanguage(LanguageDAO.findById(rs.getInt(Column.LANGUAGE_ID)));
            country.setName(rs.getString(Column.NAME));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return country;
    }
}
