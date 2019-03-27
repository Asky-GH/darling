package kz.epam.darling.dao;

import kz.epam.darling.constant.Column;
import kz.epam.darling.model.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    private static final Logger LOGGER = LogManager.getLogger(CityDAO.class.getName());
    private static final String FIND_ALL_QUERY = "SELECT * FROM cities";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM cities WHERE id=? AND language_id=?";
    private static final String FIND_BY_COUNTRY_ID_QUERY = "SELECT * FROM cities WHERE country_id=? AND language_id=?";
    private static final String FIND_IDS_QUERY = "SELECT DISTINCT id FROM cities";
    private static final String CREATE_QUERY = "INSERT INTO cities(id, language_id, name, country_id) VALUES(?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE cities SET name=? WHERE id=? AND language_id=?";

    public static City findById(int cityId, int languageId) {
        City city = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_ID_QUERY)) {
            ps.setInt(1, cityId);
            ps.setInt(2, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    city = retrieveCity(rs, languageId);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return city;
    }

    public static List<City> findByCountryId(int countryId, int languageId) {
        List<City> cities = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_COUNTRY_ID_QUERY)) {
            ps.setInt(1, countryId);
            ps.setInt(2, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    City city = retrieveCity(rs, languageId);
                    cities.add(city);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return cities;
    }

    public static List<City> findAll() {
        List<City> cities = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_ALL_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                City city = retrieveCity(rs, rs.getInt(Column.LANGUAGE_ID));
                cities.add(city);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return cities;
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

    public static void create(City city) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(CREATE_QUERY)) {
            ps.setInt(1, city.getId());
            ps.setInt(2, city.getLanguage().getId());
            ps.setString(3, city.getName());
            ps.setInt(4, city.getCountry().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void update(City city) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {
            ps.setString(1, city.getName());
            ps.setInt(2, city.getId());
            ps.setInt(3, city.getLanguage().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    private static City retrieveCity(ResultSet rs, int languageId) {
        City city = new City();
        try {
            city.setId(rs.getInt(Column.ID));
            city.setLanguage(LanguageDAO.findById(languageId));
            city.setName(rs.getString(Column.NAME));
            city.setCountry(CountryDAO.findById(rs.getInt(Column.COUNTRY_ID), languageId));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return city;
    }
}
