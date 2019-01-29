package kz.epam.darling.model.dao;

import kz.epam.darling.model.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    private static final Logger LOGGER = LogManager.getLogger(CountryDAO.class.getName());


    public static Country findById(Integer id) {
        Country country = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM countries WHERE id = ?")) {
            ps.setInt(1, id);
            country = retrieveCountry(ps);
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
            country = retrieveCountry(ps);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return country;
    }

    private static Country retrieveCountry(PreparedStatement ps) {
        Country country = null;
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                country = new Country();
                country.setId(rs.getInt("id"));
                country.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return country;
    }
}
