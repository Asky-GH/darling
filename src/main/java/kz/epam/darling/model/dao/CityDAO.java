package kz.epam.darling.model.dao;

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


    public static City findById(int city_id) {
        City city = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM cities WHERE id = ?")) {
            ps.setInt(1, city_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    city = retrieveCity(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return city;
    }

    public static List<City> findByCountryId(int country_id) {
        List<City> cities = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM cities WHERE country_id = ?")) {
            ps.setInt(1, country_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    City city = retrieveCity(rs);
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

    private static City retrieveCity(ResultSet rs) {
        City city = new City();
        try {
            city.setId(rs.getInt("id"));
            city.setName(rs.getString("name"));
            city.setCountry_id(rs.getInt("country_id"));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return city;
    }
}
